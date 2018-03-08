package io.transwarp.tdc.gn.common.transport;

/**
 * 18-2-8 created by zado
 */
public class TSubscription {

    private String topic;

    private String subscriber;

    private long subscribeTime;

    public TSubscription() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }
}
