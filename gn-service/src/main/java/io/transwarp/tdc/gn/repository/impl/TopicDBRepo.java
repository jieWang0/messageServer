package io.transwarp.tdc.gn.repository.impl;

import io.transwarp.tdc.gn.mapper.TopicMapper;
import io.transwarp.tdc.gn.model.Topic;
import io.transwarp.tdc.gn.repository.TopicRepo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 18-2-6 created by zado
 */
@Repository("topicDBRepo")
public class TopicDBRepo implements TopicRepo {

    @Resource
    private TopicMapper topicMapper;

    @Override
    public Topic save(Topic topic) {
        topicMapper.insert(topic);
        return topic;
    }

    @Override
    public List<Topic> save(List<Topic> topics) {
        if (topics == null || topics.isEmpty()) {
            return new ArrayList<>();
        }
        topicMapper.insertList(topics);
        return topics;
    }

    @Override
    public Topic findOne(String name) {
        return topicMapper.selectOneByName(name);
    }

    @Override
    public List<Topic> findAll() {
        return topicMapper.selectAll();
    }
}
