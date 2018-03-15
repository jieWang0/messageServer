package io.ts.tdc.gn.service;

import io.ts.tdc.gn.model.Records;

/**
 * 18-2-6 created by zado
 */
public interface ConsumerService {

//    Record consume(String topic, String subscriber, boolean autoCommit);

    Records consumeBatch(String topic, String subscriber, Integer count, String user);

    void heartbeat(String topic, String subscriber, String user);

    void leave(String topic, String subscriber, String user);
}
