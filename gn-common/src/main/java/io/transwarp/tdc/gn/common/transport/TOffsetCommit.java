package io.transwarp.tdc.gn.common.transport;

/**
 * 18-2-23 created by zado
 */
public class TOffsetCommit {

    private String subscriber;

    private String topic;

    private long offset;

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

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
