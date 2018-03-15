package io.ts.tdc.gn.service.db;

import io.ts.tdc.dlock.annotation.DLockMayAcquired;
import io.ts.tdc.dlock.model.DLock;
import io.ts.tdc.dlock.service.DLockContext;
import io.ts.tdc.gn.dlock.GNLockType;
import io.ts.tdc.gn.model.ConsumerOffset;
import io.ts.tdc.gn.model.Record;
import io.ts.tdc.gn.model.Records;
import io.ts.tdc.gn.repository.ConsumerOffsetRepo;
import io.ts.tdc.gn.repository.RecordRepo;
import io.ts.tdc.gn.service.ConsumerService;
import io.ts.tdc.gn.service.condition.DatabaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 18-2-8 created by zado
 */
@Conditional(DatabaseImpl.class)
@Service
public class ConsumerDBService implements ConsumerService {
    private static final int DEFAULT_CONSUMER_EXPIRE_MILLIS = 30 * 1000; // 30 seconds

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDBService.class);

    @Resource(name = "recordDBRepo")
    private RecordRepo recordRepo;

    @Resource(name = "consumerOffsetDBRepo")
    private ConsumerOffsetRepo consumerOffsetRepo;

    @Resource
    private DLockContext lockContext;

    @DLockMayAcquired(lockName = GNLockType.CONSUME_RECORD_VALUE)
    @Override
    public Records consumeBatch(String topic, String subscriber, Integer count, String user) {
        ConsumerOffset consumerOffset = consumerOffsetRepo.findOneByTopicAndSubscriber(topic, subscriber);

        DLock lock = lockContext.getDLock();
        if (lock == null) {
            LOGGER.debug("Lock not acquired, returns empty record set");
            return Records.empty(isSameUser(user, consumerOffset.getLastUser()), true);
        }

        if (count != null && count < 0) {
            LOGGER.warn("Count less than zero is regarded as consume all");
            count = null;
        }

        // check if the user is same as last consumption
        if (!shouldConsume(consumerOffset, user)) {
            return Records.empty(false, false);
        }

        long preOffset = consumerOffset.getCurrentOffset();
        LOGGER.debug("Subscriber[{}] start to consume topic[{}] batch after offset[{}]", subscriber, topic, preOffset);

        List<Record> records = recordRepo.findByTopicAndOffset(topic, preOffset, count);

        return Records.data(records);
    }

    private boolean isSameUser(String user1, String user2) {
        return user1 != null && user2 != null && user1.equals(user2);
    }

    @Override
    public void heartbeat(String topic, String subscriber, String user) {
        LOGGER.debug("Heartbeat for topic {} subscriber {} from user {}", topic, subscriber, user);
        ConsumerOffset consumerOffset = consumerOffsetRepo.findOneByTopicAndSubscriber(topic, subscriber);
        if (consumerOffset != null && isSameUser(consumerOffset.getLastUser(), user)) {
            consumerOffsetRepo.updateLastActiveTime(topic, subscriber, System.currentTimeMillis());
        }
    }

    @Override
    public void leave(String topic, String subscriber, String user) {
        LOGGER.debug("User[{}] leaves group[{}] upon tipic[{}]", user, subscriber, topic);
        ConsumerOffset consumerOffset = consumerOffsetRepo.findOneByTopicAndSubscriber(topic, subscriber);
        if (consumerOffset.getLastUser() != null && consumerOffset.getLastUser().equals(user)) {

        }
    }

    private boolean shouldConsume(ConsumerOffset consumerOffset, String user) {
        if (consumerOffset == null) {
            // no offset for given subscriber and topic
            return false;
        }
        if (user == null) {
            // not specify user means to consume no matter who
            return true;
        }
        if (consumerOffset.getLastUser() == null) {
            // the previous user is unknown, can consume
            return true;
        }
        if (consumerOffset.getLastUser().equalsIgnoreCase(user)) {
            return true;
        }
        if (consumerOffset.getLastActiveTime() < System.currentTimeMillis() - DEFAULT_CONSUMER_EXPIRE_MILLIS) {
            return true;
        }
        return false;
    }
}
