package io.ts.tdc.gn.client.db;

import io.ts.tdc.gn.client.produce.Producer;
import io.ts.tdc.gn.client.config.AbstractProducerConfig;
import io.ts.tdc.gn.client.rest.GNRestClient;
import io.ts.tdc.gn.client.rest.GNRestClientFactory;
import io.ts.tdc.gn.client.rest.GNRestConfig;
import io.ts.tdc.gn.common.NotificationProducerRecord;
import io.ts.tdc.gn.common.seder.PayloadSerializer;
import io.ts.tdc.gn.common.transport.TPayload;
import io.ts.tdc.gn.client.config.AbstractProducerConfig;
import io.ts.tdc.gn.client.produce.Producer;
import io.ts.tdc.gn.client.rest.GNRestClient;
import io.ts.tdc.gn.client.rest.GNRestClientFactory;
import io.ts.tdc.gn.client.rest.GNRestConfig;
import io.ts.tdc.gn.common.NotificationProducerRecord;
import io.ts.tdc.gn.common.seder.PayloadSerializer;
import io.ts.tdc.gn.common.transport.TPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * 18-2-9 created by zado
 */
public class DBProducer<T> implements Producer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBProducer.class);

    private Map<String, Object> configs;

    private GNRestClient client;
    private PayloadSerializer<T> serializer;

    public DBProducer(Map<String, Object> configs) {
        if (configs == null) {
            throw new IllegalArgumentException("Producer configs can not be null");
        }
        this.configs = configs;
        initSerializer();
        buildClient();
    }

    @Override
    public void send(NotificationProducerRecord<T> record) {
        LOGGER.debug("Start to produce a record via DBProducer");
        TPayload payload = new TPayload();
        payload.setPayload(serializer.serialize(record.payload()));
        client.produce(record.topic(), payload , null);
        LOGGER.debug("Success to produce a record via DBProducer");
    }

    @Override
    public void close() {
        // db type producer need nothing to close
        // maybe
    }

    @SuppressWarnings("unchecked")
    private void initSerializer() {
        if (configs.get(AbstractProducerConfig.PAYLOAD_SERIALIZER) == null) {
            throw new IllegalArgumentException("DBProducer serializer cannot be null.");
        } else {
            this.serializer = (PayloadSerializer<T>) configs.get(AbstractProducerConfig.PAYLOAD_SERIALIZER);
        }
    }

    private void buildClient() {
        GNRestConfig clientConfig = new GNRestConfig();

        if (configs.get(DBProducerConfig.SERVER_LOCATION) == null) {
            throw new IllegalArgumentException("DBProducer server location is not configured");
        }
        clientConfig.setLocation((String) configs.get(DBProducerConfig.SERVER_LOCATION));

        if (configs.get(DBProducerConfig.SERVER_TIMEOUT_ENABLE) != null) {
            clientConfig.setTimeoutEnabled((Boolean) configs.get(DBProducerConfig.SERVER_TIMEOUT_ENABLE));
        }
        if (configs.get(DBProducerConfig.SERVER_TIMEOUT_MILLIS) != null) {
            clientConfig.setTimeoutInMillis((Integer) configs.get(DBProducerConfig.SERVER_TIMEOUT_MILLIS));
        }

        this.client = GNRestClientFactory.create(clientConfig);

        LOGGER.info("Success to build DBProducer client");
    }
}
