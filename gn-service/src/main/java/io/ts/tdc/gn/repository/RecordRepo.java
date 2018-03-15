package io.ts.tdc.gn.repository;

import io.ts.tdc.gn.model.Record;
import io.ts.tdc.gn.model.Record;

import java.util.List;

/**
 * 18-2-6 created by zado
 */
public interface RecordRepo {

    Record save(Record record);

    /**
     * 找到topic下的最新的消息
     *
     * @return 如果没有则返回null
     */
    Record findOneByTopicOrderByOffset(String topic);

    /**
     * 找到topic下offset位置后的第一条
     *
     * @return 如果没有记录则返回null
     */
    Record findOneByTopicAndOffset(String topic, long offset);

    /**
     * 查询topic下offset位置后的limit条数
     *
     * @param limit 如果limit为null, 则返回所有的
     * @return
     */
    List<Record> findByTopicAndOffset(String topic, long offset, Integer limit);
}
