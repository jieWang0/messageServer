package io.transwarp.tdc.gn.service.db;

import io.transwarp.tdc.gn.common.exception.ErrorCode;
import io.transwarp.tdc.gn.common.exception.GNException;
import io.transwarp.tdc.gn.common.utils.GeneratorX;
import io.transwarp.tdc.gn.model.Record;
import io.transwarp.tdc.gn.repository.RecordRepo;
import io.transwarp.tdc.gn.repository.TopicRepo;
import io.transwarp.tdc.gn.service.ProducerService;
import io.transwarp.tdc.gn.service.condition.DatabaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 18-2-6 created by zado
 */
@Conditional(DatabaseImpl.class)
@Service("producerDBService")
public class ProducerDBService implements ProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDBService.class);

    @Resource(name = "topicDBRepo")
    private TopicRepo topicRepo;

    @Resource(name = "recordDBRepo")
    private RecordRepo recordRepo;

    @Override
    public void produce(String topic, String payload) {
        if (topicRepo.findOne(topic) == null) {
            throw new GNException(ErrorCode.TOPIC_NOT_FOUND,
                String.format("Cannot send to a topic[name=%s] which is not existed", topic));
        }

        Record record = new Record();
        record.setOffset(GeneratorX.generateUid());// 唯一的offset标识
        record.setTopic(topic);
        record.setPayload(payload);
        record.setCreateTime(new Date().getTime());

        recordRepo.save(record);

        LOGGER.debug("Success to send message to topic[name={}]", topic);
    }
}
