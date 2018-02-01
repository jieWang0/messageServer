package io.transwarp.tdc.gn.client.consume;

import io.transwarp.tdc.gn.common.NotificationConsumerRecord;
import io.transwarp.tdc.gn.common.PayloadSerializer;

public interface ConsumeRecordPersistStrategy {

    /**
     * persist record
     * @param consumerRecord
     */
    void persist(NotificationConsumerRecord consumerRecord);

    /**
     * inject payload serializer
     * @param serializer
     */
    void setSerializer(PayloadSerializer serializer);
}
