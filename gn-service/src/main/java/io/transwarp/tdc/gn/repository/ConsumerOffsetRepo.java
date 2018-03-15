package io.transwarp.tdc.gn.repository;

import io.transwarp.tdc.gn.model.ConsumerOffset;

/**
 * 18-2-6 created by zado
 */
public interface ConsumerOffsetRepo {

    ConsumerOffset save(ConsumerOffset consumerOffset);

    ConsumerOffset findOneByTopicAndSubscriber(String topic, String subscriber);

    void update(ConsumerOffset consumerOffset);

    void deleteByTopicAndSubscriber(String topic, String subscriber);

    void updateLastActiveTime(String topic, String subscriber, long currentTime);
}
