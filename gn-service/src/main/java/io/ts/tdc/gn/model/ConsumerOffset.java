package io.ts.tdc.gn.model;

/**
 * 18-2-6 created by zado
 */
public class ConsumerOffset {

    private String topic;

    /**
     * might be multiple user using same subscriber name
     */
    private String subscriber;

    private long currentOffset;

    private long commitTime;

    /**
     * record the last successful user consuming the topic
     */
    private String lastUser;

    private long lastActiveTime;

    public ConsumerOffset() {
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

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    public long getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(long lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }
}
