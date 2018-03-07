package io.transwarp.tdc.gn.service;

import io.transwarp.tdc.gn.model.Subscription;

import java.util.List;

/**
 * 18-2-6 created by zado
 */
public interface SubscriptionService {

    void subscribe(String subscriber, String topic);

    void unsubscribe(String subscriber, String topic);

    List<Subscription> listSubscriptions(String subscriber);
}
