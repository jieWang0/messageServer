package io.ts.tdc.gn.service.kafka;

import io.ts.tdc.gn.common.NotificationProducerRecord;

public interface ProduceErrorStrategy {

    /**
     * deal with produce error
     * @param errorRecord
     */
    void handleProduceError(NotificationProducerRecord errorRecord);

    /**
     * inject error persist strategy
     * @param persistStrategy
     */
    void setPersistStrategy(ProduceErrorPersistStrategy persistStrategy);
}
