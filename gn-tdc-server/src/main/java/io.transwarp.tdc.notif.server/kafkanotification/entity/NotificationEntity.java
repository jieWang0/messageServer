package io.transwarp.tdc.notif.server.kafkanotification.entity;

import java.io.Serializable;

public class NotificationEntity implements Serializable{
    private Integer id;
    private String topic;
    private String message;

    public NotificationEntity(Integer id,String topoc, String message) {
        this.id = id;
        this.topic = topoc;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
