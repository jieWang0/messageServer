package io.transwarp.tdc.gn.common;

public interface NotificationConsumerRecord<T> {

    String guid();

    String topic();

    String group();

    int partition();

    long offset();

    long createTime();

    T payload();
}
