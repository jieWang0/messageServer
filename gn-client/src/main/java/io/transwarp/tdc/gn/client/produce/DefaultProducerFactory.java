package io.transwarp.tdc.gn.client.produce;

import io.transwarp.tdc.gn.client.db.DBProducer;

import java.util.Map;

/**
 * 对于producer都是采用同样的调用rest api的方式来发送的, 都是一个producer
 *
 * 18-2-23 created by zado
 */
public class DefaultProducerFactory<T> implements ProducerFactory<T> {

    private Map<String, Object> configs;

    public DefaultProducerFactory(Map<String, Object> configs) {
        if (configs == null) {
            throw new IllegalArgumentException("Producer Factory configs can not be null");
        }
        this.configs = configs;
    }

    @Override
    public Producer<T> getInstance() {
        return new DBProducer<>(configs);
    }
}
