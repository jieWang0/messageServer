package io.ts.tdc.gn.client;

import io.ts.tdc.gn.client.consume.Consumer;
import io.ts.tdc.gn.client.consume.ConsumerArgs;
import io.ts.tdc.gn.client.db.DBConsumer;
import io.ts.tdc.gn.client.db.DBConsumerConfig;
import io.ts.tdc.gn.common.NotificationConsumerRecord;
import io.ts.tdc.gn.common.NotificationConsumerRecords;
import io.ts.tdc.gn.common.seder.JacksonPayloadDeserializer;
import io.ts.tdc.gn.common.seder.StringPayloadDeserializer;
import io.ts.tdc.tracing.retrofit.RetrofitArgs;
import io.ts.tdc.gn.client.consume.Consumer;
import io.ts.tdc.gn.client.consume.ConsumerArgs;
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

        ConsumerArgs consumerArgs = new ConsumerArgs.Builder()
                .topic("aimer")
                .group("zado")
                .retrofitArgs(new RetrofitArgs.Builder().setLocation(serverLocation).build())
                .additionalArg(DBConsumerConfig.PAYLOAD_DESERIALIZER, new StringPayloadDeserializer())
                .build();

        Consumer<String> consumer = new DBConsumer<>(consumerArgs);

//        Map<String, Object> configs = new HashMap<>();
//        configs.put(DBConsumerConfig.PAYLOAD_DESERIALIZER, new StringPayloadDeserializer());
//        configs.put(DBConsumerConfig.SERVER_LOCATION, serverLocation);
//        configs.put(DBConsumerConfig.CONSUMER_TOPIC, "aimer");
//        configs.put(DBConsumerConfig.CONSUMER_GROUP, "zado");
//
//        Consumer<String> consumer = new DBConsumer<>(configs);

//        NotificationConsumerRecord<String> strOnce = consumer.pollOnce(1000);

        NotificationConsumerRecords<String> strBatch = consumer.poll(1000);

        strBatch.forEach(s -> System.out.println(s.payload()));
    }

//    @Test
//    public void testConsumeOnce() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(DBConsumerConfig.PAYLOAD_DESERIALIZER, new JacksonPayloadDeserializer<>(Aimer.class));
//        configs.put(DBConsumerConfig.SERVER_LOCATION, serverLocation);
//        configs.put(DBConsumerConfig.CONSUMER_TOPIC, "yoki");
//        configs.put(DBConsumerConfig.CONSUMER_GROUP, "zado");
//
//        Consumer<Aimer> consumer = new DBConsumer<>(configs);
//
//        NotificationConsumerRecord<Aimer> aimerOnce = consumer.pollOnce(1000);
//        System.out.println(aimerOnce);
//    }
}
