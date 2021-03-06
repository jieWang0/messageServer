package io.ts.tdc.gn.repository;

import io.ts.tdc.gn.model.Subscription;
import io.ts.tdc.gn.model.Subscription;

import java.util.List;

/**
 * 18-2-6 created by zado
 */
public interface SubscriptionRepo {

    Subscription save(Subscription subscription);

    Subscription findOneBySubscriberAndTopic(String subscriber, String topic);

    List<Subscription> findBySubscriber(String subscriber);

    void deleteBySubscriberAndTopic(String subscriber, String topic);
}
