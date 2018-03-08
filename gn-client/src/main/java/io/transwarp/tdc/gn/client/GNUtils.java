package io.transwarp.tdc.gn.client;

import io.transwarp.tdc.gn.common.ConsumerOffset;

import java.util.List;

/**
 * 18-2-23 created by zado
 */
public interface GNUtils {

    ConsumerOffset fetchConsumerOffset(String group, String topic);

    void subscribe(String group, String topic);

    void unsubscribe(String group, String topic);

    List<String> listTopics();
}
