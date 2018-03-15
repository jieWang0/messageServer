package io.ts.tdc.gn.client;

import io.ts.tdc.gn.common.NotificationConsumerRecord;
import io.ts.tdc.gn.common.seder.PayloadSerializer;
import io.ts.tdc.gn.common.NotificationConsumerRecord;

public interface ConsumePersistStrategy<T> extends AutoCloseable {

    /**
     * persist record
     * @param record
     */
    void persist(NotificationConsumerRecord<T> record);

    default void close() {}

    default boolean shouldPersistBeforeConsume() { return false; }

}
