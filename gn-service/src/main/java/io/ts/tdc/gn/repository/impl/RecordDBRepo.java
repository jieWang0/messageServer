package io.ts.tdc.gn.repository.impl;

import io.ts.tdc.gn.mapper.RecordMapper;
import io.ts.tdc.gn.model.Record;
import io.ts.tdc.gn.repository.RecordRepo;
import io.ts.tdc.gn.mapper.RecordMapper;
import io.ts.tdc.gn.model.Record;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 18-2-6 created by zado
 */
@Repository("recordDBRepo")
public class RecordDBRepo implements RecordRepo {

    @Resource
    private RecordMapper recordMapper;

    @Override
    public Record save(Record record) {
        recordMapper.insertUseGeneratedKeys(record);
        return record;
    }

    @Override
    public Record findOneByTopicOrderByOffset(String topic) {
        return recordMapper.selectOneByTopicOrderByOffsetDesc(topic);
    }

    @Override
    public Record findOneByTopicAndOffset(String topic, long offset) {
        return recordMapper.selectOneByTopicAndOffsetOrderByOffsetAsc(topic, offset);
    }

    @Override
    public List<Record> findByTopicAndOffset(String topic, long offset, Integer limit) {
        return recordMapper.selectByTopicAndOffsetOrderByOffsetAscLimit(topic, offset, limit);
    }
}
