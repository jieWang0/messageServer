package io.transwarp.tdc.gn.service;

import io.transwarp.tdc.gn.model.ConsumerOffset;

/**
 * 18-2-7 created by zado
 */
public interface ConsumerOffsetService {

    /**
     * 获取订阅者当前读取的消息位置
     */
    ConsumerOffset fetch(String topic, String subscriber);

    /**
     * 提交读取的位置
     */
    void commit(String topic, String subscriber, long offset);
}
