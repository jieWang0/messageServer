package io.ts.tdc.gn.client.produce;

import io.ts.tdc.gn.common.NotificationProducerRecord;

public interface Producer<T> {

    void send(NotificationProducerRecord<T> record);

    void close();
}
