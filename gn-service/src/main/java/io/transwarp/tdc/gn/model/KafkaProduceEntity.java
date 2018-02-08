package io.transwarp.tdc.gn.model;

import java.io.Serializable;

public class KafkaProduceEntity implements Serializable{
    private String id;
    private String topic;
    private String message;
    private Long createTime;

    public KafkaProduceEntity(String id, String topic, String message,Long createTime) {
        this.id = id;
        this.topic = topic;
        this.message = message;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
