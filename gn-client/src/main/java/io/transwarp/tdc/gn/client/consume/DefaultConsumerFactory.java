package io.transwarp.tdc.gn.client.consume;

import io.transwarp.tdc.gn.client.config.GenericConfig;
import io.transwarp.tdc.gn.client.db.DBConsumer;
import io.transwarp.tdc.gn.client.rest.GNRestClient;
import io.transwarp.tdc.gn.client.rest.GNRestClientFactory;
import io.transwarp.tdc.gn.client.rest.GNRestConfig;
import io.transwarp.tdc.gn.common.NotificationStorageType;
import io.transwarp.tdc.gn.common.transport.TMetaInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 18-2-23 created by zado
 */
public class DefaultConsumerFactory<T> implements ConsumerFactory<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultConsumerFactory.class);

    private Map<String, Object> configs;
    private GNRestClient client;
    private NotificationStorageType type;

    public DefaultConsumerFactory(Map<String, Object> configs) {
        if (configs == null) {
            throw new IllegalArgumentException("Consumer factory configs cannot be null");
        }
        this.configs = configs;

        buildSimpleClient();
        getGNType();
    }

    @Override
    public Consumer<T> getInstance() {
        Consumer<T> consumer = null;
        switch (type) {
            case DATABASE:
                consumer = new DBConsumer<>(configs);
                LOGGER.info("Success to create a DBConsumer");
                break;
            case KAFKA:
                // todo
                break;
            default:
                LOGGER.warn("Unknown consumer type, cannot create a consumer");
                break;
        }

        return consumer;
    }

    private void buildSimpleClient() {
        GNRestConfig clientConfig = new GNRestConfig();

        if (configs.get(GenericConfig.GN_SERVER_LOCATION) == null) {
            throw new IllegalArgumentException("Generic notification server location is not configured");
        }
        clientConfig.setLocation((String) configs.get(GenericConfig.GN_SERVER_LOCATION));

        this.client = GNRestClientFactory.create(clientConfig);
    }

    private void getGNType() {
        TMetaInfo metaInfo = client.getMetaInfo();
        if (NotificationStorageType.DATABASE.name().equals(metaInfo.getType())) {
            this.type = NotificationStorageType.DATABASE;
        } else if (NotificationStorageType.KAFKA.name().equals(metaInfo.getType())) {
            this.type = NotificationStorageType.KAFKA;
        } else {
            throw new IllegalStateException("Unknown generic notification type: " + metaInfo.getType());
        }
    }
}
