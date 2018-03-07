package io.transwarp.tdc.gn.service.db;

import io.transwarp.tdc.gn.model.Topic;
import io.transwarp.tdc.gn.repository.TopicRepo;
import io.transwarp.tdc.gn.service.TopicService;
import io.transwarp.tdc.gn.service.condition.DatabaseImpl;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 18-2-6 created by zado
 */
@Conditional(DatabaseImpl.class)
@Service("topicDBService")
public class TopicDBService implements TopicService {

    @Resource(name = "topicDBRepo")
    private TopicRepo topicRepo;

    @Override
    public List<Topic> listTopics() {
        return topicRepo.findAll();
    }

    @Override
    public Topic getTopic(String name) {
        return topicRepo.findOne(name);
    }

    @Override
    public void createTopic() {
        Topic topic0 = new Topic();
        topic0.setName("aimer");
        topic0.setCreator("zado");
        topic0.setCreateTime(new Date().getTime());
//        topicRepo.save(topic0);

        Topic topic1 = new Topic();
        topic1.setName("yoki");
        topic1.setCreator("zado");
        topic1.setCreateTime(new Date().getTime());
//        topicRepo.save(topic1);

        Topic topic2 = new Topic();
        topic2.setName("xx");
        topic2.setCreator("zado");
        topic2.setCreateTime(new Date().getTime());
//        topicRepo.save(topic2);

        topicRepo.save(Arrays.asList(topic0, topic1, topic2));
    }
}
