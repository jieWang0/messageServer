package io.transwarp.tdc.gn.service;

import io.transwarp.tdc.gn.common.NotificationProducerRecord;
import io.transwarp.tdc.gn.common.PayloadSerializer;

public interface NotificationService {

    /**
     * provide the information of connection for client
     * @return
     */
    ConnectionInfo getConnectionInfo();

    /**
     * send producer record to MQ/DB
     * @param record
     */
    <T> void send(NotificationProducerRecord<T> record);

    /**
     * close the service
     */
    void close();

    /**
     * inject serializer
     * @param serializer
     */
    void setSerializer(PayloadSerializer serializer);
}
