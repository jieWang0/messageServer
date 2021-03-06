package io.ts.tdc.gn.repository.impl;

import io.ts.tdc.gn.mapper.SubscriptionMapper;
import io.ts.tdc.gn.model.Subscription;
import io.ts.tdc.gn.repository.SubscriptionRepo;
import io.ts.tdc.gn.mapper.SubscriptionMapper;
import io.ts.tdc.gn.model.Subscription;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 18-2-6 created by zado
 */
@Repository("subscriptionDBRepo")
public class SubscriptionDBRepo implements SubscriptionRepo {

    @Resource
    private SubscriptionMapper subscriptionMapper;

    @Override
    public Subscription save(Subscription subscription) {
        subscriptionMapper.insert(subscription);

        return subscription;
    }

    @Override
    public Subscription findOneBySubscriberAndTopic(String subscriber, String topic) {
        return subscriptionMapper.selectOneBySubscriberAndTopic(subscriber, topic);
    }

    @Override
    public List<Subscription> findBySubscriber(String subscriber) {
        return subscriptionMapper.selectBySubscriber(subscriber);
    }

    @Override
    public void deleteBySubscriberAndTopic(String subscriber, String topic) {
        subscriptionMapper.deleteBySubscriberAndTopic(subscriber, topic);
    }
}
