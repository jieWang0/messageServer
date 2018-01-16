package io.transwarp.tdc.notif.client;

import io.transwarp.tdc.notif.common.NProducerRecord;

import java.util.Collection;

public interface Producer<T> {

    void send(NProducerRecord<T> record);

    void send(Collection<NProducerRecord> records);

    void close();
}
