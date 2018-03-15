package io.ts.tdc.gn.client.kafka;

import io.ts.tdc.gn.common.NotificationConsumerRecord;

public class KafkaConsumerRecord<T> implements NotificationConsumerRecord<T> {

    private final String topic;
    private final int partition;
    private final long offset;
    private final T payLoad;
    private final long createTime;
    private final String group;

    KafkaConsumerRecord(String topic,int partition,long offset,T payLoad,long createTime,String group) {
        this.createTime = createTime;
        this.offset = offset;
        this.payLoad = payLoad;
        this.partition = partition;
        this.topic = topic;
        this.group = group;
    }

    public static class Builder<T> {
        private  String topic;
        private  int partition;
        private  long offset;
        private  T payLoad;
        private  long createTime;
        private  String group;
        Builder(){}
        public Builder<T> topic(String topic) {
            this.topic = topic;
            return this;
        }
        public Builder<T> partition(int partition) {
            this.partition = partition;
            return this;
        }
        public Builder<T> offset(long offset) {
            this.offset = offset;
            return this;
        }
        public  Builder<T> playLoad(T payLoad) {
            this.payLoad = payLoad;
            return this;
        }
        public Builder<T> createTime(long createTime) {
            this.createTime = createTime;
            return this;
        }
        public Builder<T> group(String group) {
            this.group = group;
            return this;
        }
        public KafkaConsumerRecord<T> build() {
            return new KafkaConsumerRecord<>(this.topic,this.partition,
                    this.offset,this.payLoad,this.createTime,this.group);
        }
    }

    @Override
    public String guid() {
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
        return partition;
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
        return payLoad;
    }
}
