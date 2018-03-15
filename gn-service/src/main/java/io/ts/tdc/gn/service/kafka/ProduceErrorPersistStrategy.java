package io.ts.tdc.gn.service.kafka;

import io.ts.tdc.gn.common.NotificationProducerRecord;

public interface ProduceErrorPersistStrategy {

    void persist(NotificationProducerRecord errorRecord);
}
