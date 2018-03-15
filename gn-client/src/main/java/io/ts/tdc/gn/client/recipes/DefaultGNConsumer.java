package io.ts.tdc.gn.client.recipes;

import io.ts.tdc.gn.client.*;
import io.ts.tdc.gn.client.consume.Consumer;
import io.ts.tdc.gn.client.consume.ConsumerArgs;
import io.ts.tdc.gn.client.db.DBConsumer;
import io.ts.tdc.gn.client.exception.CommitFailedException;
import io.ts.tdc.gn.client.exception.ShutdownException;
import io.ts.tdc.gn.client.kafka.GnKafkaConsumer;
import io.ts.tdc.gn.client.meta.MetaInfoRetriever;
import io.ts.tdc.gn.common.MetaInfo;
import io.ts.tdc.gn.common.NotificationConsumerRecord;
import io.ts.tdc.gn.common.NotificationConsumerRecords;
import io.ts.tdc.gn.common.PartitionOffset;
import io.ts.tdc.gn.common.config.ConfigConstants;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;
import io.ts.tdc.gn.common.seder.SerdeFactory;
import io.ts.tdc.gn.client.consume.Consumer;
import io.ts.tdc.gn.client.consume.ConsumerArgs;
import io.ts.tdc.gn.client.exception.CommitFailedException;
import io.ts.tdc.gn.client.exception.ShutdownException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Default implementation
 * @param <T>
 */
public class DefaultGNConsumer<T> implements GNConsumer<T> {
    private static final AtomicInteger consumerCounter = new AtomicInteger(0);
    private static final Logger logger = LoggerFactory.getLogger(DefaultGNConsumer.class);

    private final Class<T> recordType;
    private final ConsumeHandler<T> consumeHandler;
    private final ConsumerArgs consumerArgs;
    private final SerdeFactory serdeFactory;
    private final ConsumePersistStrategy<T> persistStrategy;
    private final ConsumeDedupeStrategy<T> dedupeStrategy;
    private ConsumeShutdownStrategy shutdownStrategy;
    private ConsumeHeartbeatDaemon heartbeatDaemon;
    private ConsumeCommitPolicy commitPolicy;
    private MetaInfoRetriever metaRetriever;

    private volatile boolean initialized;
    private Thread consumerThread;
    private CountDownLatch shutdownLatch = new CountDownLatch(1);

    private Consumer<T> consumer;

    DefaultGNConsumer(Class<T> recordType, ConsumeHandler<T> consumeHandler,
                      ConsumerArgs consumerArgs, SerdeFactory serdeFactory,
                      ConsumePersistStrategy<T> persistStrategy,
                      ConsumeDedupeStrategy<T> dedupeStrategy,
                      ConsumeShutdownStrategy shutdownStrategy,
                      ConsumeHeartbeatDaemon heartbeatDaemon,
                      ConsumeCommitPolicy commitPolicy,
                      MetaInfoRetriever metaRetriever) {
        this.recordType = recordType;
        this.consumeHandler = Objects.requireNonNull(consumeHandler, "ConsumeHandler cannot be null");
        this.consumerArgs = Objects.requireNonNull(consumerArgs, "ConsumerArgs cannot be null");
        this.serdeFactory = serdeFactory;
        this.persistStrategy = persistStrategy;
        this.dedupeStrategy = dedupeStrategy;
        this.shutdownStrategy = Objects.requireNonNull(shutdownStrategy, "Shutdown strategy cannot be null");
        this.heartbeatDaemon = Objects.requireNonNull(heartbeatDaemon, "Heartbeat daemon cannot be null");
        this.commitPolicy = Objects.requireNonNull(commitPolicy, "Commit policy cannot be null");
        this.metaRetriever = Objects.requireNonNull(metaRetriever, "MetaInfoRetriever cannot be null");
    }

    @Override
    public void start() {
        if (initialized) {
            throw new RuntimeException("GNConsumer cannot be started more than once");
        }
        initialized = true;
        consumer = buildConsumer();
        consumerThread = new Thread(this::loop);
        consumerThread.setName("GN-Consumer-" + consumerCounter.incrementAndGet());
        consumerThread.start();
        heartbeatDaemon.start();
    }

    private Consumer<T> buildConsumer() {
        // get backend type to determine the impl of client
        MetaInfo metaInfo = metaRetriever.metaInfo();
        Map<String, Object> configs = new HashMap<>();
        mergeConfigs(consumerArgs, configs);

        switch (metaInfo.getBackendType()) {
            case KAFKA:
                return new GnKafkaConsumer<>(consumerArgs,metaInfo.getServerUrl());
            case DATABASE:
                configs.put(ConfigConstants.CONSUMER_SERVICE_LOCATION, metaRetriever.metaInfoSource());
                return new DBConsumer<>(consumerArgs);
            default:
                throw new GNException(ErrorCode.META_INFO_ERROR, "Unsupported backend type: " + metaInfo.getBackendType());
        }
    }

    private void mergeConfigs(ConsumerArgs consumerArgs, Map<String, Object> configs) {
        configs.put(ConfigConstants.CONSUMER_POLL_TIMEOUT_MILLIS, consumerArgs.getPollTimeoutMillis());
        configs.put(ConfigConstants.CONSUMER_AUTO_COMMIT_ENABLED, consumerArgs.isAutoCommitEnabled());
        configs.put(ConfigConstants.CONSUMER_AUTO_COMMIT_INTERVAL_MILLIS, consumerArgs.getAutoCommitIntervalMillis());
    }

    @Override
    public void shutdown() {
        shutdownStrategy.shutdown();
    }

    @Override
    public void shutdownAwait(long timeoutMillis) throws InterruptedException {
        shutdownStrategy.shutdown();
        shutdownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public Class<T> getType() {
        return recordType;
    }

    @Override
    public void setShutdownStrategy(ConsumeShutdownStrategy shutdownStrategy) {
        if (initialized) {
            throw new IllegalStateException("Shutdown strategy cannot be changed as the consumer is already started");
        }
        this.shutdownStrategy = shutdownStrategy;
    }

    @Override
    public void setHeartbeatDaemon(ConsumeHeartbeatDaemon heartbeatDaemon) {
        if (initialized) {
            throw new IllegalStateException("heartbeat daemon cannot be changed as the consumer is already started");
        }
        this.heartbeatDaemon = heartbeatDaemon;
    }

    private void loop() {
        try {
            while (!shutdownStrategy.isShutdown()) {
                NotificationConsumerRecords<T> records = consumer.poll(consumerArgs.getPollTimeoutMillis());
                if (shutdownStrategy.isShutdown()) {
                    logger.info("Shutdown command called, quit the processing.");
                    break;
                }

                PartitionOffsetRecorder recorder = new PartitionOffsetRecorder();
                for (NotificationConsumerRecord<T> record: records) {
                    if (dedupeStrategy != null && dedupeStrategy.isDuplicated(record)) {
                        logger.trace("Record polled, Detected as duplicated, committing as consumed.");
                        consumer.commit(Collections.singleton(new PartitionOffset(record.partition(), record.offset())));
                        continue;
                    }

                    if (persistStrategy != null && persistStrategy.shouldPersistBeforeConsume()) {
                        persistStrategy.persist(record);
                    }

                    try {
                        consumeHandler.processRecord(record);
                    } catch (Exception e) {
                        consumeHandler.handleException(e, record);
                    }

                    if (persistStrategy != null && !persistStrategy.shouldPersistBeforeConsume()) {
                        persistStrategy.persist(record);
                    }
                    recorder.inc(record.partition(), record.offset());
                    if (commitPolicy == ConsumeCommitPolicy.Single) {
                        consumer.commit(Collections.singleton(new PartitionOffset(record.partition(), record.offset())));
                    }
                }
                Collection<PartitionOffset> offsets = recorder.get();
                if (!offsets.isEmpty() && commitPolicy == ConsumeCommitPolicy.Batch) {
                    try {
                        consumer.commit(offsets);
                    } catch (CommitFailedException cfe) {
                        logger.error("Commit failed", cfe);
                    }
                }
            }
        } catch (ShutdownException se) {
            /* a exception-based shutdown action, can be ignored safely */
        } finally {
            try {
                if (dedupeStrategy != null) {
                    dedupeStrategy.close();
                }
                if (persistStrategy != null) {
                    persistStrategy.close();
                }
            } finally {
                shutdownLatch.countDown();
            }
        }
    }
}