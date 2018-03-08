package io.transwarp.tdc.gn.client.db;

import io.transwarp.tdc.gn.common.NotificationProducerRecord;

import java.util.Date;

/**
 * 18-2-9 created by zado
 */
public class DBProducerRecord<T> implements NotificationProducerRecord<T> {

    private String topic;
    private T payload;
    private long createTime;

    public DBProducerRecord(String topic, T payload) {
        this(topic, payload, new Date().getTime());
    }

    public DBProducerRecord(String topic, T payload, long createTime) {
        if (topic == null || topic.isEmpty()) {
            throw new IllegalArgumentException("Topic can not be null");
        }
        this.topic = topic;
        this.payload = payload;
        this.createTime = createTime;
    }

    @Override
    public String guid() {
        // ignore in database type consumer
        return null;
    }

    @Override
    public String topic() {
        return topic;
    }

    @Override
    public int partition() {
        // ignore in database type consumer
        // return a never existed number
        return -1;
    }

    @Override
    public long createTime() {
        return createTime;
    }

    @Override
    public T payload() {
        return payload;
    }
}
