package io.transwarp.tdc.gn.client.db;

import io.transwarp.tdc.gn.client.consume.Consumer;
import io.transwarp.tdc.gn.client.rest.GNRestClient;
import io.transwarp.tdc.gn.client.rest.GNRestClientFactory;
import io.transwarp.tdc.gn.client.rest.GNRestConfig;
import io.transwarp.tdc.gn.common.NotificationConsumerRecord;
import io.transwarp.tdc.gn.common.transport.TOffsetCommit;
import io.transwarp.tdc.gn.common.transport.TRecord;
import io.transwarp.tdc.gn.common.PartitionOffset;
import io.transwarp.tdc.gn.common.seder.PayloadDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 18-2-8 created by zado
 */
public class DBConsumer<T> implements Consumer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBConsumer.class);

    private Map<String, Object> configs;

    private String topic;
    private String group;
    private boolean autoCommit;
    private PayloadDeserializer<T> deserializer;
    private GNRestClient client;

    public DBConsumer(Map<String, Object> configs) {
        this(configs,
            (String) configs.get(DBConsumerConfig.CONSUMER_TOPIC),
            (String) configs.get(DBConsumerConfig.CONSUMER_GROUP));
    }

    public DBConsumer(Map<String, Object> configs, String topic, String group) {
        if (configs == null) {
            throw new IllegalArgumentException("DBConsumer configs cannot be null");
        }

        if (group == null || topic == null) {
            throw new IllegalArgumentException("DBConsumer either topic or group cannot be null");
        }

        this.configs = configs;
        this.topic = topic;
        this.group = group;

        if (configs.get(DBConsumerConfig.AUTO_COMMIT_ENABLE) != null) {
            this.autoCommit = (boolean) configs.get(DBConsumerConfig.AUTO_COMMIT_ENABLE);
        } else {
            // 默认开启auto commit
            LOGGER.info("DBConsumer group[{}] on topic[{}] set default auto-commit true", group, topic);
            this.autoCommit = true;
        }

        initDeserializer();
        buildClient();
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
    public NotificationConsumerRecord<T> pollOnce(long timeoutMillis) {
        // todo
        // timeoutMillis not used now because client 自带 timeout

        LOGGER.debug("Consume[group={}] start to poll one record on topic[{}]", group, topic);
        TRecord tRecord = client.consumeOne(topic, group, autoCommit);
        LOGGER.debug("Consume[group={}] success to poll one record on topic[{}]", group, topic);

        if (tRecord.isNil()) {
            return null;
        } else {
            return buildRecord(tRecord);
        }
    }

    @Override
    public List<NotificationConsumerRecord<T>> poll(long timeoutMillis) {
        // todo
        // timeoutMillis not used now because client 自带 timeout

        LOGGER.debug("Consume[group={}] start to poll records on topic[{}]", group, topic);
        List<TRecord> tRecords = client.consumeBatch(topic, group, null, autoCommit);
        LOGGER.debug("Consume[group={}] success to poll one record on topic[{}]", group, topic);

        return buildRecords(tRecords);
    }

    @Override
    public void commit(Collection<PartitionOffset> partitionOffsets) {
        if (partitionOffsets == null) {
            return;
        }

        for (PartitionOffset partitionOffset : partitionOffsets) {
            // database实现中不需要使用partition
            TOffsetCommit offsetCommit = new TOffsetCommit();
            offsetCommit.setTopic(topic);
            offsetCommit.setSubscriber(group);
            offsetCommit.setOffset(partitionOffset.getOffset());

            client.commitOffset(offsetCommit);
            LOGGER.debug("Consume[group={}] success to commit offsets on topic[{}]", group, topic);
        }
    }

    @Override
    public void close() {
        // db consumer need nothing to close
    }

    @SuppressWarnings("unchecked")
    private void initDeserializer() {
        if (configs.get(DBConsumerConfig.PAYLOAD_DESERIALIZER) == null) {
            throw new IllegalArgumentException("DBConsumer deserializer cannot be null");
        } else {
            this.deserializer = (PayloadDeserializer<T>) configs.get(DBConsumerConfig.PAYLOAD_DESERIALIZER);
        }
    }

    private void buildClient() {
        GNRestConfig clientConfig = new GNRestConfig();

        if (configs.get(DBConsumerConfig.SERVER_LOCATION) == null) {
            throw new IllegalArgumentException("DBConsumer server location is not configured");
        }
        clientConfig.setLocation((String) configs.get(DBConsumerConfig.SERVER_LOCATION));

        if (configs.get(DBConsumerConfig.SERVER_TIMEOUT_ENABLE) != null) {
            clientConfig.setTimeoutEnabled((Boolean) configs.get(DBConsumerConfig.AUTO_COMMIT_ENABLE));
        }
        if (configs.get(DBConsumerConfig.SERVER_TIMEOUT_MILLIS) != null) {
            clientConfig.setTimeoutInMillis((Integer) configs.get(DBConsumerConfig.SERVER_TIMEOUT_MILLIS));
        }

        this.client = GNRestClientFactory.create(clientConfig);
    }

    private DBConsumerRecord<T> buildRecord(TRecord tRecord) {
        if (tRecord == null) {
            return null;
        } else {
            return new DBConsumerRecord.Builder<T>()
                           .topic(tRecord.getTopic())
                           .group(group)
                           .offset(tRecord.getOffset())
                           .payload(deserializer.deserialize(tRecord.getPayload()))
                           .createTime(tRecord.getCreateTime())
                       .build();
        }
    }

    private List<NotificationConsumerRecord<T>> buildRecords(List<TRecord> tRecords) {
        List<NotificationConsumerRecord<T>> records = new ArrayList<>();
        if (tRecords != null) {
            for (TRecord tRecord : tRecords) {
                records.add(buildRecord(tRecord));
            }
        }

        return records;
    }
}
