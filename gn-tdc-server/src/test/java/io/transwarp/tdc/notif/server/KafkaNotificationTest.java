package io.transwarp.tdc.notif.server;

import io.transwarp.tdc.gn.service.kafka.KafkaConsumerConfigInfo;
import io.transwarp.tdc.gn.service.kafka.KafkaProducerConfigInfo;
import io.transwarp.tdc.gn.common.exception.GNException;
import io.transwarp.tdc.gn.model.KafkaProduceEntity;
import io.transwarp.tdc.gn.repository.impl.KafkaProduceDao;
import io.transwarp.tdc.gn.server.GnTdcServerApplication;
import io.transwarp.tdc.gn.service.kafka.impl.KafkaProducerRecord;
import io.transwarp.tdc.gn.service.kafka.impl.KafkaProducerService;
import kafka.server.KafkaServer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

@SpringApplicationConfiguration(classes = GnTdcServerApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class KafkaNotificationTest {

    @Autowired
     KafkaProducerConfigInfo kafkaProducerConfigInfo;

    @Autowired
     KafkaConsumerConfigInfo kafkaConsumerConfigInfo;

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Autowired
    private KafkaProduceDao kafkaProduceDao;

    private static String topic = "kafka-topic-test";
    private static KafkaServer kafkaTestServer;
    private static KafkaConsumer<String,String> consumer;
    private static String test_message = "test_kafka";
    @ClassRule
    public static KafkaEmbedded kafkaEmbedded = new KafkaEmbedded(1,true,1,topic);


    @BeforeClass
    public static void before() {
        Properties props = new Properties();
        props.put("bootstrap.servers",kafkaEmbedded.getBrokersAsString());
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
    }

    @Test
    public void produce() {
        kafkaProducerConfigInfo.setBootstrapServers(kafkaEmbedded.getBrokersAsString());
        kafkaConsumerConfigInfo.setBootstrapServers(kafkaEmbedded.getBrokersAsString());
        KafkaProducerRecord<String> record = new KafkaProducerRecord<>(topic,test_message);
        try {
            kafkaProducerService.send(record,false);
        } catch (GNException e) {
            System.out.println(e.getErrorCode()+e.getMessage());
            return;
        }
        checkSend();
    }

    @Test
    public void ensureProduce() {
        kafkaProducerConfigInfo.setBootstrapServers("127.0.0.1:0000");
        KafkaProducerRecord<String> record = new KafkaProducerRecord<>(topic,test_message);
        kafkaProducerService.send(record,true);
        checkDB(record.getGuid());
    }

    public void checkSend() {
        consumer.assign(Arrays.asList(new TopicPartition(topic,0)));
        long position = consumer.position(new TopicPartition(topic,0));
        consumer.seek(new TopicPartition(topic,0),position-1);
        ConsumerRecords<String, String> records = consumer.poll(600);
        for (TopicPartition partition : records.partitions()) {
            List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
            Assert.assertEquals(partitionRecords.get(0).value(),test_message);
        }
    }

    public void checkDB(String id) {
        List<KafkaProduceEntity> messageList = kafkaProduceDao.getFailedProduce();
        messageList.forEach(entity -> {
            if(entity.getId().equals(id)) {
                return;
            }
        });
        Assert.assertFalse("存储数据库失败",false);
    }
}
