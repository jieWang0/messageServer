package io.ts.tdc.gn.client;

import io.ts.tdc.gn.client.kafka.GnKafkaConsumer;
import io.ts.tdc.gn.client.kafka.KafkaConsumerConfig;
import io.ts.tdc.gn.common.GnTopicPartition;
import io.ts.tdc.gn.common.NotificationConsumerRecord;
import io.ts.tdc.gn.client.kafka.GnKafkaConsumer;
import io.ts.tdc.gn.client.kafka.KafkaConsumerConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class TestKafkaConsumer {

    private String serverLocation;

    @Before
    public void setUp() throws Exception {
        this.serverLocation = "http://localhost:9090";
    }

    @Test
    public void testConsume() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("consumer.server.location","localhost:9092");
        configs.put("topic","wj");
        configs.put("group","wjtest");
        GnKafkaConsumer<String> consumer = new GnKafkaConsumer<>(configs);
        consumer.assign(Arrays.asList(new GnTopicPartition("wj",0)));
        consumer.seekToBeginning(Arrays.asList(new GnTopicPartition("wj",0)));
        List<NotificationConsumerRecord<String>> strBatch = consumer.poll(1000);
        strBatch.forEach(s -> System.out.println(s.offset()+s.payload()));
    }


    @Test
    public void testCommit() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("consumer.server.location","localhost:9092");
        configs.put("topic","wj");
        configs.put("group","test");
        configs.put(KafkaConsumerConfig.MAX_POLL_RECORDS,1);
        configs.put(KafkaConsumerConfig.AUTO_COMMIT_ENABLE,"false");
        GnKafkaConsumer<String> consumer = new GnKafkaConsumer<>(configs);
        consumer.subscribe(Arrays.asList("wj"));
        //consumer.assign(Arrays.asList(new GnTopicPartition("wj",0)));
        //consumer.seekToBeginning(Arrays.asList(new GnTopicPartition("wj",0)));


        while (true) {
            List<NotificationConsumerRecord<String>> str = consumer.poll(0);
            str.forEach(s -> System.out.println(s.offset()+s.payload()+"first"));

        }

    }

}
