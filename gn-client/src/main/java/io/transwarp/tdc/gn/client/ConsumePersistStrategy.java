package io.transwarp.tdc.gn.client;

import io.transwarp.tdc.gn.common.NotificationConsumerRecord;
import io.transwarp.tdc.gn.common.seder.PayloadSerializer;

public interface ConsumePersistStrategy<T> extends AutoCloseable {

    /**
     * persist record
     * @param record
     */
    void persist(NotificationConsumerRecord<T> record);

    default void close() {}

    default boolean shouldPersistBeforeConsume() { return false; }

}
