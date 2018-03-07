package io.transwarp.tdc.gn.client.db;

import io.transwarp.tdc.gn.common.NotificationConsumerRecord;

/**
 * 18-2-9 created by zado
 */
public class DBConsumerRecord<T> implements NotificationConsumerRecord<T> {

    private final String topic;
    private final String group;
    private final long offset;
    private final T payload;
    private final long createTime;

    public DBConsumerRecord(String topic, String group, long offset,
                            T payload, long createTime) {
        this.topic = topic;
        this.group = group;
        this.offset = offset;
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
    public String group() {
        return group;
    }

    @Override
    public int partition() {
        // ignore in database type consumer
        // return a never existed number
        return -1;
    }

    @Override
    public long offset() {
        return offset;
    }

    @Override
    public long createTime() {
        return createTime;
    }

    @Override
    public T payload() {
        return payload;
    }

    public static class Builder<T> {
        private String topic;
        private String group;
        private long offset;
        private T payload;
        private long createTime;

        public Builder<T> topic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder<T> group(String group) {
            this.group = group;
            return this;
        }

        public Builder<T> offset(long offset) {
            this.offset = offset;
            return this;
        }

        public Builder<T> payload(T payload) {
            this.payload = payload;
            return this;
        }

        public Builder<T> createTime(long createTime) {
            this.createTime = createTime;
            return this;
        }

        public DBConsumerRecord<T> build() {
            return new DBConsumerRecord<>(topic, group, offset, payload, createTime);
        }
    }
}
