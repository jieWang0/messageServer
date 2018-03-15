package io.ts.tdc.gn.service.db;

import io.ts.tdc.dlock.annotation.DLockAcquired;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;
import io.ts.tdc.gn.dlock.GNLockType;
import io.ts.tdc.gn.model.ConsumerOffset;
import io.ts.tdc.gn.repository.ConsumerOffsetRepo;
import io.ts.tdc.gn.service.ConsumerOffsetService;
import io.ts.tdc.gn.service.condition.DatabaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 18-2-7 created by zado
 */
@Conditional(DatabaseImpl.class)
@Service("consumerOffsetDBService")
public class ConsumerOffsetDBService implements ConsumerOffsetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerOffsetDBService.class);

    @Resource(name = "consumerOffsetDBRepo")
    private ConsumerOffsetRepo consumerOffsetRepo;

    @Override
    public ConsumerOffset fetch(String topic, String subscriber) {
        ConsumerOffset consumerOffset = consumerOffsetRepo.findOneByTopicAndSubscriber(topic, subscriber);
        if (consumerOffset == null) {
            throw new GNException(ErrorCode.TOPIC_NOT_SUBSCRIBED,
                String.format("Subscriber[%s] does not subscribe topic[%s] can not fetch the consumer offset", subscriber, topic));
        }

        return consumerOffset;
    }

    @DLockAcquired(lockName = GNLockType.CONSUME_OFFSET_VALUE)
    @Override
    public void commit(String topic, String subscriber, long offset) {
        ConsumerOffset consumerOffset = consumerOffsetRepo.findOneByTopicAndSubscriber(topic, subscriber);
        if (consumerOffset == null) {
            throw new GNException(ErrorCode.TOPIC_NOT_SUBSCRIBED,
                String.format("Subscriber[%s] does not subscribe topic[%s] can not commit the consumer offset", subscriber, topic));
        }

        if (offset <= consumerOffset.getCurrentOffset()) {
            throw new GNException(ErrorCode.OFFSET_COMMIT_ERROR, "Cannot commit the offset which is early than the current");
        }

        consumerOffset.setCurrentOffset(offset);
        consumerOffset.setCommitTime(new Date().getTime());

        consumerOffsetRepo.update(consumerOffset);
        LOGGER.debug("Subscriber[{}] success to commit offset on topic[{}]", subscriber, topic);
    }
}
