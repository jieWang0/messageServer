package io.transwarp.tdc.gn.model;

import java.io.Serializable;

public class KafkaProduceEntity implements Serializable{
    private Long id;
    private String topic;
    private String message;

    public KafkaProduceEntity(Long id, String topoc, String message) {
        this.id = id;
        this.topic = topoc;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
