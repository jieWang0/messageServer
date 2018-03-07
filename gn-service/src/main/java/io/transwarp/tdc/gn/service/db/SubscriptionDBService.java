package io.transwarp.tdc.gn.service.db;

import io.transwarp.tdc.gn.common.exception.ErrorCode;
import io.transwarp.tdc.gn.common.exception.GNException;
import io.transwarp.tdc.gn.model.ConsumerOffset;
import io.transwarp.tdc.gn.model.Record;
import io.transwarp.tdc.gn.model.Subscription;
import io.transwarp.tdc.gn.repository.ConsumerOffsetRepo;
import io.transwarp.tdc.gn.repository.RecordRepo;
import io.transwarp.tdc.gn.repository.SubscriptionRepo;
import io.transwarp.tdc.gn.repository.TopicRepo;
import io.transwarp.tdc.gn.service.SubscriptionService;
import io.transwarp.tdc.gn.service.condition.DatabaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 18-2-6 created by zado
 */
@Conditional(DatabaseImpl.class)
@Service
public class SubscriptionDBService implements SubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionDBService.class);

    @Resource(name = "subscriptionDBRepo")
    private SubscriptionRepo subscriptionRepo;

    @Resource(name = "topicDBRepo")
    private TopicRepo topicRepo;

    @Resource(name = "consumerOffsetDBRepo")
    private ConsumerOffsetRepo consumerOffsetRepo;

    @Resource(name = "recordDBRepo")
    private RecordRepo recordRepo;

    @Transactional
    @Override
    public void subscribe(String subscriber, String topic) {
        if (topicRepo.findOne(topic) == null) {
            throw new GNException(ErrorCode.TOPIC_NOT_FOUND,
                String.format("Topic[name=%s] is not found, cannot subscribe", topic));
        }

        if (subscriptionRepo.findOneBySubscriberAndTopic(subscriber, topic) != null) {
            LOGGER.warn("Subscriber[{}] is already subscribed the topic[{}] before.", subscriber, topic);
            return;
        }

        long subscribeTime = new Date().getTime();

        createSubscription(topic, subscriber, subscribeTime);
        createConsumerOffset(topic, subscriber, subscribeTime);

        LOGGER.info("Subscriber[{}] success to subscribe topic[{}]", subscriber, topic);
    }

    @Transactional
    @Override
    public void unsubscribe(String subscriber, String topic) {
        // 从订阅表中删除
        subscriptionRepo.deleteBySubscriberAndTopic(subscriber, topic);

        // 删除消费的记录
        consumerOffsetRepo.deleteByTopicAndSubscriber(topic, subscriber);

        LOGGER.info("Subscriber[{}] success to unsubscribe topic[{}]", subscriber, topic);
    }

    @Override
    public List<Subscription> listSubscriptions(String subscriber) {
        return subscriptionRepo.findBySubscriber(subscriber);
    }

    private void createSubscription(String topic, String subscriber, long subscribeTime) {
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setSubscriber(subscriber);
        subscription.setSubscribeTime(subscribeTime);

        subscriptionRepo.save(subscription);
    }

    private void createConsumerOffset(String topic, String subscriber, long subscribeTime) {
        ConsumerOffset consumerOffset = new ConsumerOffset();
        consumerOffset.setTopic(topic);
        consumerOffset.setSubscriber(subscriber);
        consumerOffset.setCommitTime(subscribeTime);
        consumerOffset.setCurrentOffset(fetchStartOffset(topic));

        if (consumerOffsetRepo.findOneByTopicAndSubscriber(subscriber, topic) != null) {
            LOGGER.warn("Subscriber[{}] has consumerOffset on topic[{}] before subscribe(should never happen)", subscriber, topic);
            consumerOffsetRepo.update(consumerOffset);
        } else {
            LOGGER.info("Create consumerOffset for subscriber[{}] on topic[{}]", subscriber, topic);
            consumerOffsetRepo.save(consumerOffset);
        }
    }

    // 获取订阅的时候用户应该消费的第一个消息位置
    // 如果没有可以消费的消息的话, 则给出一个-1
    private long fetchStartOffset(String topic) {
        Record record = recordRepo.findOneByTopicOrderByOffset(topic);
        return record == null ? -1 : record.getOffset();
    }
}
