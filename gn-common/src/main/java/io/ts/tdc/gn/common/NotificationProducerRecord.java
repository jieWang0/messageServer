package io.ts.tdc.gn.common;

public interface NotificationProducerRecord<T> {

    String guid();

    String topic();

    int partition();

    long createTime();

    T payload();

}
