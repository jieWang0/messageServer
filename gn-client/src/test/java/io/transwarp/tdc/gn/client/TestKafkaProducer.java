package io.transwarp.tdc.gn.client;

import io.transwarp.tdc.gn.client.config.AbstractProducerConfig;
import io.transwarp.tdc.gn.client.config.GenericConfig;
import io.transwarp.tdc.gn.client.db.DBProducer;
import io.transwarp.tdc.gn.client.db.DBProducerConfig;
import io.transwarp.tdc.gn.client.db.DBProducerRecord;
import io.transwarp.tdc.gn.client.produce.Producer;
import io.transwarp.tdc.gn.common.NotificationProducerRecord;
import io.transwarp.tdc.gn.common.seder.StringPayloadSerializer;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestKafkaProducer {
    @Test
    public void send() {
        Producer<String> producer;
        Map<String, Object> configs = new HashMap<>();

        configs.put(GenericConfig.GN_SERVER_LOCATION, "http://localhost:8080");
        configs.put(DBProducerConfig.SERVER_LOCATION, "http://localhost:8080");

        producer = new DBProducer<>(configs);
        String topic = "aimer";
        NotificationProducerRecord<String> record = new DBProducerRecord<>(topic, "haahahahaha");

        producer.send(record);
    }

    public void ensureSend() {
        Producer<String> producer;
        Map<String, Object> configs = new HashMap<>();

        configs.put(GenericConfig.GN_SERVER_LOCATION, "http://localhost:23333");
        configs.put(AbstractProducerConfig.PAYLOAD_SERIALIZER, new StringPayloadSerializer());
        configs.put(DBProducerConfig.SERVER_LOCATION, "http://localhost:23333");

        producer = new DBProducer<>(configs);
        String topic = "aimer";
        NotificationProducerRecord<String> record = new DBProducerRecord<>(topic, "haahahahaha");

        producer.send(record);
    }

}
