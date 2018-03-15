package io.ts.tdc.gn.service;

import io.ts.tdc.gn.common.NotificationProducerRecord;
import io.ts.tdc.gn.model.MetaInfo;

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
