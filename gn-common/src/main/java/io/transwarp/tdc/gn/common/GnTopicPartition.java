package io.transwarp.tdc.gn.common;

public class GnTopicPartition {
    private String topic;
    private int partition;
    public GnTopicPartition(String topic, int partition) {
        if(topic == null) {
            throw new IllegalArgumentException("topic is illegal");
        }
        this.topic = topic;
        this.partition = partition;
    }

    public String topic() {
        return topic;
    }

    public int partition() {
        return partition;
    }
}
