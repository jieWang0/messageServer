package io.ts.tdc.gn.service.db;

import io.ts.tdc.gn.common.NotificationProducerRecord;

public interface ProduceRecordPersistStrategy {

    /**
     * persist the produce record to deal with send failure
     * @param producerRecord
     */
    void persist(NotificationProducerRecord producerRecord);

    /**
     * mark the produce record as finished
     * @param producerRecord
     */
    void finish(NotificationProducerRecord producerRecord);
}
