package io.transwarp.tdc.gn.client.kafka;

import io.transwarp.tdc.gn.client.consume.Consumer;
import io.transwarp.tdc.gn.common.NotificationConsumerRecord;
import io.transwarp.tdc.gn.common.GnTopicPartition;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GnKafkaConsumer<T> implements Consumer<T> {
    private String group;
    private String topic;
    private KafkaConsumer<String,T> consumer;
    private static final Logger logger = LoggerFactory.getLogger(GnKafkaConsumer.class);
    public GnKafkaConsumer(Map<String, Object> configs) {
        if (configs.get("group") == null || configs.get("topic") == null) {
            throw new IllegalArgumentException("GnKafkaConsumer either topic or group cannot be null");
        }
        this.topic = (String) configs.get("topic");
        this.group = (String) configs.get("group");

        if (configs.get(KafkaConsumerConfig.SERVER_LOCATION) == null) {
            throw new IllegalArgumentException("GnKafkaConsumer server location is not configured");
        }
        this.consumer = getKafkaConsumerInstance(configs);
    }

    @Override
    public NotificationConsumerRecord<T> pollOnce(long timeoutMillis) {
        return null;
    }

    @Override
    public List<NotificationConsumerRecord<T>> poll(long timeoutMillis) {
        ConsumerRecords<String,T> consumerRecords = this.consumer.poll(timeoutMillis);
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

    public void subscribe(Collection<String> topics) {
        this.consumer.subscribe(topics);
    }
    public void assign(Collection<GnTopicPartition> partitions) {
        List<TopicPartition> kafkaPartitions = new ArrayList<>();
        partitions.forEach(partition->{
            kafkaPartitions.add(new TopicPartition(partition.topic(),partition.partition()));
        });
        this.consumer.assign(kafkaPartitions);
    }
    public void seek(String topic ,int partition,long offset) {
        this.consumer.seek(new TopicPartition(topic,partition),offset);
    }

    public void seekToEnd(Collection<GnTopicPartition> partitions){
        List<TopicPartition> kafkaPartitions = new ArrayList<>();
        partitions.forEach(partition->{
            kafkaPartitions.add(new TopicPartition(partition.topic(),partition.partition()));
        });
        this.consumer.seekToEnd(kafkaPartitions);
    }

    public void seekToBeginning(Collection<GnTopicPartition> partitions) {
        List<TopicPartition> kafkaPartitions = new ArrayList<>();
        partitions.forEach(partition->{
            kafkaPartitions.add(new TopicPartition(partition.topic(),partition.partition()));
        });
        this.consumer.seekToBeginning(kafkaPartitions);
    }

    public long position(String topic,int partition) {
        return this.consumer.position(new TopicPartition(topic,partition));
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

    private KafkaConsumer<String,T> getKafkaConsumerInstance(Map<String, Object> configs) {

        Properties properties = new Properties();
        if(!configs.containsKey(KafkaConsumerConfig.AUTO_COMMIT_ENABLE)) {
            properties.put("enable.auto.commit","true");
        } else {
            properties.put("enable.auto.commit",String.valueOf(configs.get(KafkaConsumerConfig.AUTO_COMMIT_ENABLE)));
        }
        properties.put("bootstrap.servers",configs.get(KafkaConsumerConfig.SERVER_LOCATION));
        properties.put("group.id",configs.get("group"));
      //  properties.put("max.partition.fetch.bytes","100");
        if(configs.containsKey(KafkaConsumerConfig.MAX_POLL_RECORDS))
             properties.put("max.poll.records",configs.get(KafkaConsumerConfig.MAX_POLL_RECORDS));

        properties.put("key.deserializer", KafkaConsumerConfig.KEY_PAYLOAD_DESERIALIZER);

        if (!configs.containsKey(KafkaConsumerConfig.VALUE_PAYLOAD_DESERIALIZER)) {
            properties.put("value.deserializer",KafkaConsumerConfig.VALUE_PAYLOAD_DESERIALIZER);
        } else {
            properties.put("value.deserializer",configs.get(KafkaConsumerConfig.PAYLOAD_DESERIALIZER));
        }

        return new KafkaConsumer<>(properties);
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
        this.consumer.close();
    }
}
