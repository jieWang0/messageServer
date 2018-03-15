package io.transwarp.tdc.gn.client.kafka;

import io.transwarp.tdc.gn.client.consume.Consumer;
import io.transwarp.tdc.gn.client.consume.ConsumerArgs;
import io.transwarp.tdc.gn.common.NotificationConsumerRecord;
import io.transwarp.tdc.gn.common.GnTopicPartition;
import io.transwarp.tdc.gn.common.NotificationConsumerRecords;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GnKafkaConsumer<T> implements Consumer<T> {
    private String group;
    private String topic;
    private KafkaConsumer<String,T> consumer;
    private Deserializer<T> keyDeserializer;
    private Deserializer<T> valueDeserializer;
    private String url;
    private static final Logger logger = LoggerFactory.getLogger(GnKafkaConsumer.class);
    public GnKafkaConsumer(ConsumerArgs consumerArgs,String url) {
        if (consumerArgs.getGroup() == null || consumerArgs.getTopic() == null) {
            throw new IllegalArgumentException("GnKafkaConsumer either topic or group cannot be null");
        }
        this.url = url;
        this.topic = consumerArgs.getTopic();
        this.group = consumerArgs.getGroup();
        this.keyDeserializer = (Deserializer<T>) consumerArgs.getAdditionalArgs().get("keyDeserializer");
        this.valueDeserializer = (Deserializer<T>) consumerArgs.getAdditionalArgs().get("valueDeserializer");
        this.consumer = getKafkaConsumerInstance(consumerArgs);

    }

    @Override
    public NotificationConsumerRecords<T> poll(long timeoutMillis) {
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

    private NotificationConsumerRecords<T> records(ConsumerRecords<String,T> records) {
        return records == null ?
                () -> new EmptyIter<>() :
                () -> new KafkaRecordsIter<>(records, group);
    }

    private static class KafkaRecordsIter<T> implements Iterator<NotificationConsumerRecord<T>> {
        private Iterator<ConsumerRecord<String,T> > iter;
        private String group;

        KafkaRecordsIter(ConsumerRecords<String,T> records,String group) {
            this.iter = records.iterator();
            this.group = group;
        }

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        @Override
        public NotificationConsumerRecord<T> next() {
            ConsumerRecord<String,T> record = iter.next();
            return new KafkaConsumerRecord.Builder<T>()
                    .createTime(record.timestamp())
                    .partition(record.partition())
                    .offset(record.offset())
                    .playLoad(record.value())
                    .topic(record.topic())
                    .group(group)
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

    private KafkaConsumer<String,T> getKafkaConsumerInstance(ConsumerArgs consumerArgs) {

        Properties properties = new Properties();
        if(!consumerArgs.isAutoCommitEnabled()) {
            properties.put("enable.auto.commit","false");
        }
        properties.put("bootstrap.servers",this.url);
        properties.put("group.id",consumerArgs.getGroup());
        properties.put("max.poll.records",consumerArgs.getPollBatchSize());
        properties.put("key.deserializer", this.keyDeserializer);
        properties.put("value.deserializer",this.valueDeserializer);
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
