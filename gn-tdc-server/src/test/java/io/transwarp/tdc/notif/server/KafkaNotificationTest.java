package io.transwarp.tdc.notif.server;

import io.transwarp.tdc.gn.common.KafkaConfigUtils;
import io.transwarp.tdc.gn.common.KafkaProducerConfigInfo;
import io.transwarp.tdc.gn.common.transport.TResult;
import io.transwarp.tdc.gn.model.KafkaProduceEntity;
import io.transwarp.tdc.gn.repository.impl.KafkaProduceDao;
import io.transwarp.tdc.gn.service.kafka.impl.KafkaProducerService;
import kafka.server.KafkaServer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
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
public class KafkaNotificationTest {

    @Autowired
    KafkaProducerConfigInfo kafkaProducerConfigInfo;

    @Autowired
    KafkaConfigUtils kafkaConfigUtils;

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Autowired
    private KafkaProduceDao kafkaProduceDao;

    private static String topic = "kafka-topic-test";
    private static KafkaServer kafkaTestServer;
    private static KafkaConsumer<String,String> consumer;
    @ClassRule
    public static KafkaEmbedded kafkaEmbedded = new KafkaEmbedded(1,true,topic);


    @BeforeClass
    public static void before() {
        /*Map<String,String> properties = new HashMap<>();
        properties.put("auto.offset.reset","earliest");
        kafkaEmbedded = kafkaEmbedded.brokerProperties(properties);
        kafkaTestServer = kafkaEmbedded.getKafkaServers().get(0);*/
        Properties props = new Properties();
        props.put("bootstrap.servers",kafkaEmbedded.getBrokersAsString());
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
    }

    @Test
    public void produce() {
        kafkaProducerConfigInfo.setBootstrapServers(kafkaEmbedded.getBrokersAsString());
        boolean result = kafkaProducerService.send(topic,null,"测试kafka");
        System.out.println("send result:"+result);
        checkSend();
    }


    @Test
    public void tryProduce() {
        kafkaProducerConfigInfo.setBootstrapServers(kafkaEmbedded.getBrokersAsString());
        TResult result = kafkaProducerService.ensureSend(topic,null,"测试kafka2");
        System.out.println(result.getResult()+result.getMessage());
        if(result.getResult().equals("success")){
            checkSend();
        } else {
            checkDB();
        }
    }

    public void checkSend() {
        ConsumerRecords<String, String> records = consumer.poll(600);
        for (TopicPartition partition : records.partitions()) {
            List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
            for (ConsumerRecord<String, String> record : partitionRecords) {
                System.out.println("offset:"+record.offset() + ": " + "value:"+record.value());
            }
        }
    }

    public void checkDB() {
        List<KafkaProduceEntity> messageList = kafkaProduceDao.getFailedProduce();
        messageList.forEach(entity -> {
            System.out.println("topic:"+entity.getTopic()+",message:"+entity.getMessage());
        });
    }
}
