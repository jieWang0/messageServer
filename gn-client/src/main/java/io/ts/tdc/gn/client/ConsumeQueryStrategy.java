package io.ts.tdc.gn.client;

import io.ts.tdc.gn.common.NotificationConsumerRecord;
import io.ts.tdc.gn.common.NotificationConsumerRecord;

public interface ConsumeQueryStrategy<T> {

    NotificationConsumerRecord<T> query(String guid);
}
