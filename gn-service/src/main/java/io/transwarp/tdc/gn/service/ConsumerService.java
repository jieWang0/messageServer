package io.transwarp.tdc.gn.service;

import io.transwarp.tdc.gn.model.Record;

import java.util.List;

/**
 * 18-2-6 created by zado
 */
public interface ConsumerService {

    Record consume(String topic, String subscriber, boolean autoCommit);

    List<Record> consumeBatch(String topic, String subscriber, Integer count, boolean autoCommit);
}
