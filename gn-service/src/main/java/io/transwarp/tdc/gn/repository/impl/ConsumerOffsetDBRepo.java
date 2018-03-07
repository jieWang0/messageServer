package io.transwarp.tdc.gn.repository.impl;

import io.transwarp.tdc.gn.mapper.ConsumerOffsetMapper;
import io.transwarp.tdc.gn.model.ConsumerOffset;
import io.transwarp.tdc.gn.repository.ConsumerOffsetRepo;
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
}
