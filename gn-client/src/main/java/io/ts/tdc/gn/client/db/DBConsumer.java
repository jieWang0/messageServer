package io.ts.tdc.gn.client.db;

import io.ts.tdc.gn.client.consume.Consumer;
import io.ts.tdc.gn.client.consume.ConsumerArgs;
import io.ts.tdc.gn.client.rest.GNRestApi;
import io.ts.tdc.gn.client.rest.GNRestClient;
import io.ts.tdc.gn.common.NotificationConsumerRecord;
import io.ts.tdc.gn.common.NotificationConsumerRecords;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;
import io.ts.tdc.gn.common.transport.TOffsetCommit;
import io.ts.tdc.gn.common.transport.TRecord;
import io.ts.tdc.gn.common.PartitionOffset;
import io.ts.tdc.gn.common.seder.PayloadDeserializer;
import io.ts.tdc.gn.common.transport.TRecords;
import io.ts.tdc.tracing.retrofit.*;
import io.ts.tdc.gn.client.consume.Consumer;
import io.ts.tdc.gn.client.consume.ConsumerArgs;
import io.ts.tdc.gn.client.rest.GNRestApi;
import io.ts.tdc.gn.client.rest.GNRestClient;
import io.ts.tdc.gn.common.NotificationConsumerRecords;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;
import io.ts.tdc.gn.common.seder.PayloadDeserializer;
import io.ts.tdc.gn.common.transport.TOffsetCommit;
import io.ts.tdc.gn.common.transport.TRecord;
import io.ts.tdc.gn.common.transport.TRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import retrofit2.Response;

import java.util.*;

/**
 * Not thread safe
 * 18-2-8 created by zado
 */
public class DBConsumer<T> implements Consumer<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBConsumer.class);

    private String topic;
    private String group;
    private String user;
    private int pollBatchSize;
    private boolean autoCommitEnabled;
    private int autoCommitIntervalMillis;
    private long lastCommitTime = 0;
    private long lastOffset = -1;

    private PayloadDeserializer<T> deserializer;
    private GNRestClient client;

    public DBConsumer(ConsumerArgs consumerArgs) {
        this.topic = consumerArgs.getTopic();
        this.group = consumerArgs.getGroup();
        this.user = consumerArgs.getUser();
        this.pollBatchSize = consumerArgs.getPollBatchSize();
        this.autoCommitEnabled = consumerArgs.isAutoCommitEnabled();
        this.autoCommitIntervalMillis = consumerArgs.getAutoCommitIntervalMillis();

        initDeserializer(consumerArgs.getAdditionalArgs());
        buildClient(consumerArgs.getRetrofitArgs(), consumerArgs.getAdditionalArgs());
    }

    @Override
    public String group() {
        return group;
    }

    @Override
    public String topic() {
        return topic;
    }

    @Override
    public NotificationConsumerRecords<T> poll(long timeoutMillis) {
        long maxDelayTime = System.currentTimeMillis() + timeoutMillis;
        if (autoCommitEnabled) {
            if (lastOffset >= 0 && lastCommitTime < System.currentTimeMillis() - autoCommitIntervalMillis) {
                commitSingleOffset(lastOffset);
            }
        }
        LOGGER.debug("Consume[group={}] start to poll records on topic[{}]", group, topic);
        TRecords records = client.consume(topic, group, pollBatchSize, user);
        LOGGER.debug("Consume[group={}] success to poll records on topic[{}]", group, topic);

        if (records.getData() == null || records.getData().isEmpty()) {
            // no records returned
            if (records.isToConsume() && records.isLocked() && System.currentTimeMillis() < maxDelayTime) {
                // only when the consumer is master but records are locked by other consumer,
                // and the rest of time is enough, will retry
                records = client.consume(topic, group, pollBatchSize, user);
                long currTime = System.currentTimeMillis();
                if (records == null && currTime < maxDelayTime) {
                    try {
                        Thread.sleep(maxDelayTime - currTime);
                    } catch (InterruptedException ie) {
                        /* ignored */
                    }
                }
            }
        }
        return buildRecords(records);
    }

    @Override
    public void commit(Collection<PartitionOffset> partitionOffsets) {
        if (partitionOffsets == null) {
            return;
        }
        for (PartitionOffset partitionOffset : partitionOffsets) {
            commitSingleOffset(partitionOffset.getOffset());
        }
        LOGGER.debug("Consume[group={}] success to commit offsets on topic[{}]", group, topic);
    }

    private void commitSingleOffset(long offset) {
        // database实现中不需要使用partition
        TOffsetCommit offsetCommit = new TOffsetCommit();
        offsetCommit.setUser(user);
        offsetCommit.setOffset(offset);
        client.commitOffset(topic, group, offsetCommit);
        lastOffset = offset;
        lastCommitTime = System.currentTimeMillis();
    }


    @Override
    public void close() {
        // db consumer need nothing to close
    }

    @SuppressWarnings("unchecked")
    private void initDeserializer(Map<String, Object> configs) {
        if (configs.get(DBConsumerConfig.PAYLOAD_DESERIALIZER) == null) {
            throw new IllegalArgumentException("DBConsumer deserializer cannot be null");
        } else {
            this.deserializer = (PayloadDeserializer<T>) configs.get(DBConsumerConfig.PAYLOAD_DESERIALIZER);
        }
    }

    private void buildClient(RetrofitArgs retrofitArgs, Map<String, Object> additionalArgs) {
        Object appCtx = null;
        if (retrofitArgs.getEnvType() == RetrofitArgs.EnvType.SPRING) {
            appCtx = additionalArgs.get(DBConsumerConfig.CONSUMER_APPLICATION_CONTEXT);
            if (appCtx == null) {
                LOGGER.warn("GenericNotification consumer has envType set to SPRING but no applicationContext " +
                        "found in additional arguments. Please set envType to NONE or add applicationContext in " +
                        "additional arguments.");
            }
        }
        this.client =
                new RetrofitBuilder<>("genericNotification", GNRestApi.class, GNRestClient.class)
                .adapter(new GNRestResponseAdapter())
                .validator(new MethodMetadataValidator())
                .args(retrofitArgs)
                .build((ApplicationContext)appCtx);

    }

    private NotificationConsumerRecords<T> buildRecords(TRecords records) {
        return records == null ?
                () -> new EmptyIter<>() :
                () -> new DBRecordsIter<>(records, deserializer, group);
    }

    private static class DBRecordsIter<T> implements Iterator<NotificationConsumerRecord<T>> {
        private PayloadDeserializer<T> deserializer;
        private Iterator<TRecord> iter;
        private String group;

        DBRecordsIter(TRecords records, PayloadDeserializer<T> deserializer, String group) {
            this.deserializer = deserializer;
            this.iter = records.getData().iterator();
            this.group = group;
        }

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        @Override
        public NotificationConsumerRecord<T> next() {
            TRecord record = iter.next();
            return new DBConsumerRecord.Builder<T>()
                    .topic(record.getTopic())
                    .group(group)
                    .offset(record.getOffset())
                    .payload(deserializer.deserialize(record.getPayload()))
                    .createTime(record.getCreateTime())
                    .build();
        }
    }

    private static class EmptyIter<T> implements Iterator<NotificationConsumerRecord<T>> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public NotificationConsumerRecord<T> next() {
            throw new NoSuchElementException();
        }
    }


    private static class GNRestResponseAdapter implements ResponseAdapter {
        private ResponseAdapter delegate = new DefaultResponseAdapter();

        @Override
        public Object adapt(Response<Object> response) {
            try {
                return delegate.adapt(response);
            } catch (RetrofitHttpException rhe) {
                throw new GNException(ErrorCode.HTTP_ERROR, "Failed with http status: " + rhe.getHttpStatus(), rhe);
            }
        }
    }
}
