package io.transwarp.tdc.gn.client;

import io.transwarp.tdc.gn.common.NotificationProducerRecord;

public interface GenericNotificationPublisher<T> {

    void send(NotificationProducerRecord<T> record);

    void close();
}
