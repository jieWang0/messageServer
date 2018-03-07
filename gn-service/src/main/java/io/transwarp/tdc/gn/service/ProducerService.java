package io.transwarp.tdc.gn.service;

/**
 * 18-2-6 created by zado
 */
public interface ProducerService {

    void produce(String topic, String payload);
}
