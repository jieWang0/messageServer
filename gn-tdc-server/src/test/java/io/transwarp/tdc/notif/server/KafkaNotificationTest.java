package com.example.kafkatest;

import com.example.kafkatest.property.ConsumerPropertis;
import kafka.server.KafkaServer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
public class MyConsumerTest {

    @Autowired
    private  static ConsumerPropertis consumerPropertis;

    private static String topic = "kafka-topic-test";
    private static KafkaServer kafkaTestServer;

    @ClassRule
    public static KafkaEmbedded kafkaEmbedded = new KafkaEmbedded(1,true,topic);


    @BeforeClass
    public static void before() {
        Map<String,String> properties = new HashMap<>();
        properties.put("auto.offset.reset","earliest");
        kafkaEmbedded = kafkaEmbedded.brokerProperties(properties);
        kafkaTestServer = kafkaEmbedded.getKafkaServers().get(0);
    }

    @Test
    public void produce() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaEmbedded.getBrokersAsString());
        props.put("acks", "all");
        props.put("retries", 0);  //设置失败重试
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);  //设置延迟发送毫秒数
        props.put("buffer.memory", 33554432);  //设置生产者缓冲区大小
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        NgProducer myProducer = new NgProducer();
        myProducer.produce(props,topic,"*************kafka Test****************");
    }


    @Test
    public void tryProduce() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaEmbedded.getBrokersAsString());
        props.put("acks", "all");
        props.put("retries", 0);  //设置失败重试
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);  //设置延迟发送毫秒数
        props.put("buffer.memory", 33554432);  //设置生产者缓冲区大小
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        NgProducer myProducer = new NgProducer();
        //myProducer.tryProduce(props, topic, "*************kafka Test****************");
    }

    @Test
    public void consume() {
        Properties props = new Properties();
        //props.put("bootstrap.servers", "localhost:9092");
        // props.put("zookeeper.connect","localhost:2181");
        props.put("bootstrap.servers",kafkaEmbedded.getBrokersAsString());
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        NgConsumer myConsumer = new NgConsumer(props);
        ConsumerRecords<String, String> records = myConsumer.consume(props,topic);

        for (TopicPartition partition : records.partitions()) {
            List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
            for (ConsumerRecord<String, String> record : partitionRecords) {
                System.out.println("offset:"+record.offset() + ": " + "value:"+record.value());
            }
           // long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
           // consumer.commitSync(Collections.singletonMap(partition,new OffsetAndMetadata(lastOffset)));
        }
        System.out.println("*************** end of poll *****************");
    }


    @Test
    public void getGroup(){

    }

    @Test
    public void partitions() {

    }

}
