package io.transwarp.tdc.gn.client;

import io.transwarp.tdc.gn.common.NotificationConsumerRecord;

public interface ConsumeHandler<T> {

    void processRecord(NotificationConsumerRecord<T> consumerRecord);

}
