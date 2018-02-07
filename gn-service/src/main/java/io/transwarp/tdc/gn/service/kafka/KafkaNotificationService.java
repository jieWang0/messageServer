package io.transwarp.tdc.gn.service.kafka;

import io.transwarp.tdc.gn.common.NotificationProducerRecord;
import io.transwarp.tdc.gn.common.transport.TResult;
import io.transwarp.tdc.gn.service.MetaInfo;
import io.transwarp.tdc.gn.service.NotificationService;

public abstract class KafkaNotificationService implements NotificationService {

    public abstract boolean send(String topic, String key, String message);
    public abstract TResult ensureSend(String topic, String key, String message);

    @Override
    public MetaInfo getMetaInfo() {
        return null;
    }

    @Override
    public <T> void send(NotificationProducerRecord<T> record) {

    }

    @Override
    public void close() {

    }



}
