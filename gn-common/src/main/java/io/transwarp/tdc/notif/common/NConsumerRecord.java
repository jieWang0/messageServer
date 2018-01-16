package io.transwarp.tdc.notif.common;

public interface NConsumerRecord<T> {

    String getTopic();

    int getPartition();

    long getOffset();

    long getCreateTime();

    T getPayload();
}
