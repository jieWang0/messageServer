package io.ts.tdc.gn.service.kafka.impl;

import io.ts.tdc.gn.common.NotificationProducerRecord;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;
import io.ts.tdc.gn.common.NotificationProducerRecord;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;

import java.util.Date;
import java.util.UUID;

public class KafkaProducerRecord<T> implements NotificationProducerRecord {
    private String topic;
    private Integer partition;
    private  T payload;
    private  Long createTime;
    private String guid;

    private KafkaProducerRecord(){}

    public KafkaProducerRecord(String topic, Integer partition, T payload) {
        if (topic == null) {
            throw new GNException(ErrorCode.INVALID_TOPIC_ERROR,"Topic cannot be null.");
        } else if (partition != null && partition < 0) {
            throw new GNException(ErrorCode.INVALID_PARTITION_ERROR,"Invalid partition:Partition number should always be non-negative or null.");
        } else {
            this.topic = topic;
            this.partition = partition;
            this.createTime = new Date().getTime();
            this.payload = payload;
            this.guid = UUID.randomUUID().toString();
        }
    }

    public KafkaProducerRecord(String topic,T payload) {
        this(topic,null,payload);
    }

    @Override
    public String guid() {
        return this.guid;
    }

    @Override
    public String topic() {
        return this.topic;
    }

    @Override
    public int partition() {
        return this.partition;
    }

    @Override
    public long createTime() {
        return this.createTime;
    }

    @Override
    public Object payload() {
        return this.payload;
    }
}
