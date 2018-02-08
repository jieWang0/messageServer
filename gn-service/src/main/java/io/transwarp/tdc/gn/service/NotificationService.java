package io.transwarp.tdc.gn.service;

import io.transwarp.tdc.gn.common.NotificationProducerRecord;
import io.transwarp.tdc.gn.common.PayloadSerializer;

public interface NotificationService {

    /**
     * provide the information of connection for client
     * @return
     */
    MetaInfo getMetaInfo();

    /**
     * send producer record to MQ/DB
     * @param record
     */
    <T> void send(NotificationProducerRecord<T> record,boolean ensureSend);

    /**
     * close the service
     */
    void close();
}
