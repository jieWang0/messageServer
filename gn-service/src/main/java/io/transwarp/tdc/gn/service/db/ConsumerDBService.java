package io.transwarp.tdc.gn.service.db;

import io.transwarp.tdc.dlock.annotation.DLockAcquired;
import io.transwarp.tdc.gn.dlock.GNLockType;
import io.transwarp.tdc.gn.model.ConsumerOffset;
import io.transwarp.tdc.gn.model.Record;
import io.transwarp.tdc.gn.repository.RecordRepo;
import io.transwarp.tdc.gn.service.ConsumerOffsetService;
import io.transwarp.tdc.gn.service.ConsumerService;
import io.transwarp.tdc.gn.service.condition.DatabaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDBService.class);

    @Resource(name = "recordDBRepo")
    private RecordRepo recordRepo;

    @Resource(name = "consumerOffsetDBService")
    private ConsumerOffsetService consumerOffsetService;

    @DLockAcquired(lockName = GNLockType.CONSUME_RECORD_VALUE)
    @Override
    public Record consume(String topic, String subscriber, boolean autoCommit) {
        ConsumerOffset consumerOffset = consumerOffsetService.fetch(topic, subscriber);

        long preOffset = consumerOffset.getCurrentOffset();
        LOGGER.debug("Subscriber[{}] start to consume topic[{}] one after offset[{}]", subscriber, topic, preOffset);

        // 获取当前订阅者最新未消费的一条记录
        Record record = recordRepo.findOneByTopicAndOffset(topic, preOffset);
        if (record == null) {
            LOGGER.debug("Subscriber[{}] consume nothing on topic[{}] after offset[{}]", subscriber, topic, preOffset);
            return null;
        }

        if (autoCommit) {
            consumerOffsetService.commit(topic, subscriber, record.getOffset());
            LOGGER.debug("Auto commit offset[{}], subscriber[{}], topic[{}] for one", record.getOffset(), subscriber, topic);
        }

        return record;
    }

    @DLockAcquired(lockName = GNLockType.CONSUME_RECORD_VALUE)
    @Override
    public List<Record> consumeBatch(String topic, String subscriber, Integer count, boolean autoCommit) {
        if (count != null && count < 0) {
            LOGGER.warn("Count less than zero is regarded as consume all");
            count = null;
        }

        ConsumerOffset consumerOffset = consumerOffsetService.fetch(topic, subscriber);

        long preOffset = consumerOffset.getCurrentOffset();
        LOGGER.debug("Subscriber[{}] start to consume topic[{}] batch after offset[{}]", subscriber, topic, preOffset);

        List<Record> records = recordRepo.findByTopicAndOffset(topic, preOffset, count);

        //当读取到内容并且自动commit的时候需要更新offset
        if (!records.isEmpty() && autoCommit) {
            long commitOffset = calCommitOffset(records);

            consumerOffsetService.commit(topic, subscriber, commitOffset);
            LOGGER.debug("Auto commit offset[{}], subscriber[{}], topic[{}] for batch", commitOffset, subscriber, topic);
        }

        return records;
    }

    private long calCommitOffset(List<Record> records) {
        if (records == null || records.isEmpty()) {
            return -1;
        }

        // 查看最大的一个offset, 理论上返回值是有序的, 最后一个就行了
        long commitOffset = records.get(records.size() - 1).getOffset();
        for (Record record : records) {
            if (record.getOffset() > commitOffset) {
                commitOffset = record.getOffset();
            }
        }

        return commitOffset;
    }
}
