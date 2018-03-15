package io.ts.tdc.notif.server;

import io.ts.tdc.notif.server.kafkanotification.service.impl.NgConsumerService;
import io.ts.tdc.notif.server.kafkanotification.service.impl.NgProducerService;
import kafka.server.KafkaServer;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scala.App;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@Component
@SpringBootTest(classes = App.class)
public class MyConsumerTest {
    @Autowired
    private NgProducerService ngProducerService;

    @Autowired
    private NgConsumerService ngConsumerService;

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
        ngProducerService.produce(props,topic,null,"*************kafka Test****************");
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
        ngProducerService.tryProduce(props,topic,null,"*************kafka Test tryProduce****************");
    }

    @Test
    public void consumeDetail() {
        Properties properties = ngConsumerService.consumeDetail();
        properties.forEach((String,Object)->{
            System.out.println(String+":"+Object.toString());
        });
    }


}
