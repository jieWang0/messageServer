package io.transwarp.tdc.gn.client;

import io.transwarp.tdc.gn.client.kafka.GnKafkaProducer;
import io.transwarp.tdc.gn.client.kafka.KafkaProducerConfig;
import io.transwarp.tdc.gn.client.kafka.KafkaProducerRecord;
import io.transwarp.tdc.gn.client.produce.Producer;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestKafkaProducer {
    @Test
    public void send() {
        Producer<String> producer;
        Map<String, Object> configs = new HashMap<>();

        configs.put(KafkaProducerConfig.SERVER_LOCATION, "http://localhost:8080");
        producer = new GnKafkaProducer<>(configs);
        String topic = "test";
        KafkaProducerRecord<String> record = new KafkaProducerRecord<>(topic, "haahahahaha");
        producer.send(record);
    }

    public void ensureSend() {
        Producer<String> producer;
        Map<String, Object> configs = new HashMap<>();
        configs.put("ensureSuccess", "true");
        configs.put(KafkaProducerConfig.SERVER_LOCATION, "http://localhost:8080");
        producer = new GnKafkaProducer<>(configs);
        String topic = "test";
        KafkaProducerRecord<String> record = new KafkaProducerRecord<>(topic, "haahahahaha");
        producer.send(record);
    }

}
