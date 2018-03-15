package io.ts.tdc.gn.client;

import io.ts.tdc.gn.common.NotificationConsumerRecord;
import io.ts.tdc.gn.common.NotificationConsumerRecord;

public interface ConsumeHandler<T> {

    void processRecord(NotificationConsumerRecord<T> consumerRecord);

    Class<?> getRecordType();

    /**
     * error handling if exception thrown when processing the record
     * @param e
     */
    default void handleException(Exception e, NotificationConsumerRecord<T> errorRecord) {
        /* ignored */
    }
}
