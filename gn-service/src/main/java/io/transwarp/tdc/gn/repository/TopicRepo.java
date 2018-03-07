package io.transwarp.tdc.gn.repository;

import io.transwarp.tdc.gn.model.Topic;

import java.util.List;

/**
 * 18-2-6 created by zado
 */
public interface TopicRepo {

    Topic save(Topic topic);

    List<Topic> save(List<Topic> topics);

    Topic findOne(String name);

    List<Topic> findAll();
}
