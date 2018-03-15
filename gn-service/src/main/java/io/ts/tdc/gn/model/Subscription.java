package io.ts.tdc.gn.model;

/**
 * 18-2-6 created by zado
 */
public class Subscription {

    private String subscriber;

    private String topic;

    private long subscribeTime;

    public Subscription() {
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }
}
