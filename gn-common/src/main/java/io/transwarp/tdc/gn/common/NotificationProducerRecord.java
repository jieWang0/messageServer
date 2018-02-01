package io.transwarp.tdc.gn.common;

public interface NotificationProducerRecord<T> {

    String getGuid();

    String getTopic();

    int getPartition();

    long getCreateTime();

    T getPayload();

}
