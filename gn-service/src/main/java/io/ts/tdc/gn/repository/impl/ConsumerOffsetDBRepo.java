package io.ts.tdc.gn.repository.impl;

import io.ts.tdc.gn.mapper.ConsumerOffsetMapper;
import io.ts.tdc.gn.model.ConsumerOffset;
import io.ts.tdc.gn.repository.ConsumerOffsetRepo;
import io.ts.tdc.gn.mapper.ConsumerOffsetMapper;
import io.ts.tdc.gn.model.ConsumerOffset;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 18-2-6 created by zado
 */
@Repository("consumerOffsetDBRepo")
public class ConsumerOffsetDBRepo implements ConsumerOffsetRepo {

    @Resource
    private ConsumerOffsetMapper consumerOffsetMapper;

    @Override
    public ConsumerOffset save(ConsumerOffset consumerOffset) {
        consumerOffsetMapper.insert(consumerOffset);
        return consumerOffset;
    }

    @Override
    public ConsumerOffset findOneByTopicAndSubscriber(String topic, String subscriber) {
        return consumerOffsetMapper.selectOneByTopicAndSubscribe(topic, subscriber);
    }

    @Override
    public void update(ConsumerOffset consumerOffset) {
        consumerOffsetMapper.updateByTopicAndSubscriber(consumerOffset);
    }

    @Override
    public void deleteByTopicAndSubscriber(String topic, String subscriber) {
        consumerOffsetMapper.deleteByTopicAndSubscriber(topic, subscriber);
    }

    @Override
    public void updateLastActiveTime(String topic, String subscriber, long currentTime) {
        consumerOffsetMapper.updateLastActiveTime(topic, subscriber, currentTime);
    }
}
