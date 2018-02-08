package io.transwarp.tdc.gn.model;

import java.io.Serializable;

public class KafkaProduceEntity implements Serializable{
    private String id;
    private String topic;
    private String message;

    public KafkaProduceEntity(String id, String topoc, String message) {
        this.id = id;
        this.topic = topoc;
        this.message = message;
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
}
