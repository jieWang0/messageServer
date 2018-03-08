package io.transwarp.tdc.gn.client.kafka;

import io.transwarp.tdc.gn.client.consume.Consumer;
import io.transwarp.tdc.gn.client.db.DBConsumerConfig;
import io.transwarp.tdc.gn.client.rest.GNRestClient;
import io.transwarp.tdc.gn.client.rest.GNRestClientFactory;
import io.transwarp.tdc.gn.client.rest.GNRestConfig;
import io.transwarp.tdc.gn.common.NotificationConsumerRecord;
import io.transwarp.tdc.gn.common.transport.TMetaInfo;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GnKafkaConsumer<T> implements Consumer<T> {
    private String group;
    private String topic;
    private boolean autoCommit;
    private GNRestClient client;
    private KafkaConsumer<String,T> consumer;
    private static final Logger logger = LoggerFactory.getLogger(GnKafkaConsumer.class);
    GnKafkaConsumer(Map<String, Object> configs, String topic, String group) {
        if (group == null || topic == null) {
            throw new IllegalArgumentException("GnKafkaConsumer either topic or group cannot be null");
        }
        this.topic = topic;
        this.group = group;

        if (configs.get(KafkaConsumerConfig.SERVER_LOCATION) == null) {
            throw new IllegalArgumentException("GnKafkaConsumer server location is not configured");
        }
        GNRestConfig config = new GNRestConfig();
        config.setLocation((String)configs.get(DBConsumerConfig.SERVER_LOCATION));
        this.client = GNRestClientFactory.create(config);
        this.consumer = getKafkaConsumerInstance(configs,group,topic);
    }

    @Override
    public NotificationConsumerRecord<T> pollOnce(long timeoutMillis) {
        return null;
    }

    @Override
    public List<NotificationConsumerRecord<T>> poll(long timeoutMillis) {
        ConsumerRecords<String,T> consumerRecords = this.consumer.poll(timeoutMillis);
        if(!this.autoCommit) {
            commit(null);
        }
        return records(consumerRecords);
    }

    @Override
    public void commit(Collection collection) {
        try {
            this.consumer.commitSync();
        }catch (CommitFailedException e) {
            logger.error("GnKafkaConsumer.consume:commit failed for offset {}",e);
            throw e;
        }
    }

    private List<NotificationConsumerRecord<T>> records(ConsumerRecords<String,T> records) {
        List<NotificationConsumerRecord<T>> formatRecords = new ArrayList<>();
        for (ConsumerRecord<String, T> record : records) {
            formatRecords.add(new KafkaConsumerRecord.Builder<T>()
                    .createTime(record.timestamp())
                    .partition(record.partition())
                    .offset(record.offset())
                    .playLoad(record.value())
                    .topic(record.topic())
                    .build());
        }
        return formatRecords;
    }

    private KafkaConsumer<String,T> getKafkaConsumerInstance(Map<String, Object> configs,String group,String topic) {

        if (configs.get(KafkaConsumerConfig.PAYLOAD_DESERIALIZER) == null)
            throw new IllegalArgumentException("KafkaConsumer deserializer cannot be null");

        TMetaInfo metaInfo = this.client.getMetaInfo();
        if(metaInfo.getType().equals("DB")) {
            throw new IllegalArgumentException("GnKafkaConsumer is not support to consume from database");
        }
        Properties properties = new Properties();
        if(!configs.containsKey(KafkaConsumerConfig.AUTO_COMMIT_ENABLE)) {
            properties.put("enable.auto.commit","true");
            this.autoCommit = true;
        } else {
            properties.put("enable.auto.commit",String.valueOf(configs.get(KafkaConsumerConfig.AUTO_COMMIT_ENABLE)));
            this.autoCommit = Boolean.parseBoolean("auto-commit.enable");
        }
        properties.put("bootstrap.servers",metaInfo.getUrl());
        properties.put("group.id",group);

        if(configs.containsKey(KafkaConsumerConfig.MAX_POLL_RECORDS))
             properties.put("max.poll.records",configs.get(KafkaConsumerConfig.MAX_POLL_RECORDS));

        properties.put("key.deserializer", KafkaConsumerConfig.KEY_PAYLOAD_DESERIALIZER);
        properties.put("value.deserializer",configs.get(KafkaConsumerConfig.PAYLOAD_DESERIALIZER));
        KafkaConsumer<String,T> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topic));
        return consumer;
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
    public void close() {

    }
}
