package io.ts.tdc.gn.common;

/**
 * 18-2-23 created by zado
 */
public class ConsumerOffset {

    private String topic;

    private String group;

    private long currentOffset;

    private long lastCommitTime;

    public ConsumerOffset() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public long getCurrentOffset() {
        return currentOffset;
    }

    public void setCurrentOffset(long currentOffset) {
        this.currentOffset = currentOffset;
    }

    public long getLastCommitTime() {
        return lastCommitTime;
    }

    public void setLastCommitTime(long lastCommitTime) {
        this.lastCommitTime = lastCommitTime;
    }
}
