package io.transwarp.tdc.notif.server.kafkanotification.service;

import java.util.Properties;

public interface INgProducerService {

    public void produce(Properties properties, String topic,String key, String message);
    public void tryProduce(Properties properties, String topic, String key,String message);
}
