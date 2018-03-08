package io.transwarp.tdc.gn.client.recipes;

import io.transwarp.tdc.gn.client.ConsumeDedupeStrategy;
import io.transwarp.tdc.gn.client.ConsumeHandler;
import io.transwarp.tdc.gn.client.ConsumePersistStrategy;
import io.transwarp.tdc.gn.client.GNConsumer;
import io.transwarp.tdc.gn.client.consume.Consumer;
import io.transwarp.tdc.gn.client.consume.DefaultConsumerFactory;
import io.transwarp.tdc.gn.client.exception.CommitFailedException;
import io.transwarp.tdc.gn.common.NotificationConsumerRecord;
import io.transwarp.tdc.gn.common.PartitionOffset;
import io.transwarp.tdc.gn.common.seder.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static io.transwarp.tdc.gn.client.config.AbstractConsumerConfig.POLL_TIMEOUT_MILLIS;

/**
 * Default implementation
 * @param <T>
 */
public class DefaultGNConsumer<T> implements GNConsumer<T> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultGNConsumer.class);

    private final Class<T> recordType;
    private final ConsumeHandler<T> consumeHandler;
    private final Converter.Factory convertFactory;
    private final Map<String, Object> options;
    private final ConsumePersistStrategy<T> persistStrategy;
    private final ConsumeDedupeStrategy<T> dedupeStrategy;
    private int pollTimeoutMillis = 5000; // 5 seconds by default

    private volatile boolean initialized;
    private volatile boolean shutdown;
    private CountDownLatch shutdownLatch = new CountDownLatch(1);

    private Consumer<T> consumer;

    DefaultGNConsumer(Class<T> recordType, ConsumeHandler<T> consumeHandler, Converter.Factory convertFactory,
                      Map<String, Object> options, ConsumePersistStrategy<T> persistStrategy,
                      ConsumeDedupeStrategy<T> dedupeStrategy) {
        this.recordType = recordType;
        this.consumeHandler = consumeHandler;
        this.convertFactory = convertFactory;
        this.options = options;
        this.persistStrategy = persistStrategy;
        this.dedupeStrategy = dedupeStrategy;
        Integer pollTimeoutMillis = (Integer) options.get(POLL_TIMEOUT_MILLIS);
        if (pollTimeoutMillis != null) {
            this.pollTimeoutMillis = pollTimeoutMillis;
        }
    }

    @Override
    public void start() {
        if (initialized) {
            throw new RuntimeException("GNConsumer cannot be started more than once");
        }
        initialized = true;
        consumer = new DefaultConsumerFactory<T>(options).getInstance();
        loop();
    }

    @Override
    public void shutdown() {
        shutdown = true;
    }

    @Override
    public void shutdownAwait(long timeoutMillis) throws InterruptedException {
        shutdown = true;
        shutdownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public Class<T> getType() {
        return recordType;
    }

    private void loop() {
        try {
            while (!shutdown) {
                List<NotificationConsumerRecord<T>> records = consumer.poll(pollTimeoutMillis);
                if (shutdown) {
                    if (records != null && !records.isEmpty()) {
                        logger.warn("Record polled but consumer is already shut down.");
                    }
                    break;
                }

                if (records == null || records.isEmpty()) {
                    continue;
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
                }
                Collection<PartitionOffset> offsets = recorder.get();
                if (offsets != null && !offsets.isEmpty()) {
                    try {
                        consumer.commit(offsets);
                    } catch (CommitFailedException cfe) {
                        logger.error("Commit failed", cfe);
                    }
                }
            }
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