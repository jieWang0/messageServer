package io.ts.tdc.gn.client.kafka;

import io.ts.tdc.gn.client.config.AbstractProducerConfig;
import io.ts.tdc.gn.client.produce.Producer;
import io.ts.tdc.gn.client.rest.GNRestClient;
import io.ts.tdc.gn.client.rest.GNRestClientFactory;
import io.ts.tdc.gn.client.rest.GNRestConfig;
import io.ts.tdc.gn.common.NotificationProducerRecord;
import io.ts.tdc.gn.common.seder.PayloadSerializer;
import io.ts.tdc.gn.common.transport.TPayload;
import io.ts.tdc.gn.client.config.AbstractProducerConfig;
import io.ts.tdc.gn.client.produce.Producer;

import java.util.Map;

public class GnKafkaProducer<T> implements Producer<T> {
    private GNRestClient client;
    private Map<String, Object> configs;
    private PayloadSerializer<T> serializer;
    private boolean ensureSuccess;

    public GnKafkaProducer(Map<String, Object> configs) {
        if (configs == null) {
            throw new IllegalArgumentException("Producer configs can not be null");
        }
        this.configs = configs;
        initSerializer();
        if (configs.get(KafkaProducerConfig.SERVER_LOCATION) == null) {
            throw new IllegalArgumentException("GnKafkaProducer server location is not configured");
        }
        GNRestConfig config = new GNRestConfig();
        config.setLocation((String) configs.get(KafkaProducerConfig.SERVER_LOCATION));
        this.client = GNRestClientFactory.create(config);
        this.ensureSuccess = configs.containsKey("ensureSuccess") && (boolean) configs.get("ensureSuccess");
    }

    @Override
    public void send(NotificationProducerRecord<T> record) {
        TPayload payload = new TPayload();
        payload.setPayload(serializer.serialize(record.payload()));
        client.produce(record.topic(), payload , ensureSuccess);
    }

    private void initSerializer() {
        if (this.configs.get(AbstractProducerConfig.PAYLOAD_SERIALIZER) == null) {
            throw new IllegalArgumentException("GnKafkaProducer serializer cannot be null.");
        } else {
            this.serializer = (PayloadSerializer<T>) configs.get(AbstractProducerConfig.PAYLOAD_SERIALIZER);
        }
    }


    @Override
    public void close() {

    }
}
