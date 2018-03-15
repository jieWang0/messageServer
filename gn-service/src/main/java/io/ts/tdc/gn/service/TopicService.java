package io.ts.tdc.gn.service;

import io.ts.tdc.gn.model.Topic;

import java.util.List;

/**
 * 18-2-6 created by zado
 */
public interface TopicService {

    List<Topic> listTopics();

    Topic getTopic(String name);

    /**
     * ***************
     * **FBI WARNING**
     * ***************
     *
     * 只有测试的时候可以使用
     */
    void createTopic();
}
