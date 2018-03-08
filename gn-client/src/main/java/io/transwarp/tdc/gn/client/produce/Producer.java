package io.transwarp.tdc.gn.client.produce;

import io.transwarp.tdc.gn.common.NotificationProducerRecord;

public interface Producer<T> {

    void send(NotificationProducerRecord<T> record);

    void close();
}
