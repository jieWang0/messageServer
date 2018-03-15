package io.ts.tdc.gn.client;

import io.ts.tdc.gn.common.NotificationConsumerRecord;
import io.ts.tdc.gn.common.NotificationConsumerRecord;

/**
 * record will be discarded if checked as duplicated
 * @param <T>
 */
public interface ConsumeDedupeStrategy<T> extends AutoCloseable {

    /**
     * check if the record is duplicated
     * @param record
     * @return
     */
    boolean isDuplicated(NotificationConsumerRecord<T> record);

    default void close() {}
}
