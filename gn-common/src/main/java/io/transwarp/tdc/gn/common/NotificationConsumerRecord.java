package io.transwarp.tdc.gn.common;

public interface NotificationConsumerRecord<T> {

    String getGuid();

    String getTopic();

    int getPartition();

    long getOffset();

    long getCreateTime();

    T getPayload();
}
