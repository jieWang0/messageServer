package io.transwarp.tdc.gn.client;

import io.transwarp.tdc.gn.common.NotificationConsumerRecord;

public interface ConsumeQueryStrategy<T> {

    NotificationConsumerRecord<T> query(String guid);
}
