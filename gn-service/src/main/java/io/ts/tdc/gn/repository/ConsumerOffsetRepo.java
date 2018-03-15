package io.ts.tdc.gn.repository;

import io.ts.tdc.gn.model.ConsumerOffset;
import io.ts.tdc.gn.model.ConsumerOffset;

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
