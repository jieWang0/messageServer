package io.ts.tdc.gn.service;

import io.ts.tdc.gn.model.Subscription;

import java.util.List;

/**
 * 18-2-6 created by zado
 */
public interface SubscriptionService {

    void subscribe(String subscriber, String topic, String user);

    /**
     * unsubscribe will remove any consumptions done on the topic
     * @param subscriber
     * @param topic
     */
    void unsubscribe(String subscriber, String topic);

    List<Subscription> listSubscriptions(String subscriber);
}
