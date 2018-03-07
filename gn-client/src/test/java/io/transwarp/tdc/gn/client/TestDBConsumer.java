package io.transwarp.tdc.gn.client;

import io.transwarp.tdc.gn.client.consume.Consumer;
import io.transwarp.tdc.gn.client.db.DBConsumer;
import io.transwarp.tdc.gn.client.db.DBConsumerConfig;
import io.transwarp.tdc.gn.common.NotificationConsumerRecord;
import io.transwarp.tdc.gn.common.seder.JacksonPayloadDeserializer;
import io.transwarp.tdc.gn.common.seder.StringPayloadDeserializer;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 18-2-11 created by zado
 */
public class TestDBConsumer {

    private String serverLocation;

    @Before
    public void setUp() throws Exception {
        this.serverLocation = "http://localhost:23333";
    }

    @Test
    public void testConsumeStr() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(DBConsumerConfig.PAYLOAD_DESERIALIZER, new StringPayloadDeserializer());
        configs.put(DBConsumerConfig.SERVER_LOCATION, serverLocation);
        configs.put(DBConsumerConfig.CONSUMER_TOPIC, "aimer");
        configs.put(DBConsumerConfig.CONSUMER_GROUP, "zado");

        Consumer<String> consumer = new DBConsumer<>(configs);

//        NotificationConsumerRecord<String> strOnce = consumer.pollOnce(1000);

        List<NotificationConsumerRecord<String>> strBatch = consumer.poll(1000);

        strBatch.forEach(s -> System.out.println(s.payload()));
    }

    @Test
    public void testConsumeOnce() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(DBConsumerConfig.PAYLOAD_DESERIALIZER, new JacksonPayloadDeserializer<>(Aimer.class));
        configs.put(DBConsumerConfig.SERVER_LOCATION, serverLocation);
        configs.put(DBConsumerConfig.CONSUMER_TOPIC, "yoki");
        configs.put(DBConsumerConfig.CONSUMER_GROUP, "zado");

        Consumer<Aimer> consumer = new DBConsumer<>(configs);

        NotificationConsumerRecord<Aimer> aimerOnce = consumer.pollOnce(1000);
        System.out.println(aimerOnce);
    }
}
