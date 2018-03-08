package io.transwarp.tdc.gn.service.kafka;

import io.transwarp.tdc.gn.common.NotificationProducerRecord;

public interface ProduceErrorPersistStrategy {

    void persist(NotificationProducerRecord errorRecord);
}
