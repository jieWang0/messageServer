package io.transwarp.tdc.gn.common.transport;

/**
 * 18-2-8 created by zado
 */
public class TConsumerOffset {

    private String topic;

    private String subscriber;

    private long currentOffset;

    private long commitTime;

    public TConsumerOffset() {
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

    public long getCurrentOffset() {
        return currentOffset;
    }

    public void setCurrentOffset(long currentOffset) {
        this.currentOffset = currentOffset;
    }

    public long getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(long commitTime) {
        this.commitTime = commitTime;
    }
}
