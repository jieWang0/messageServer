package io.ts.tdc.gn.mapper;

import io.ts.mangix.web.mybatis.BaseMapper;
import io.ts.tdc.gn.model.Record;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 18-2-6 created by zado
 */
public interface RecordMapper extends BaseMapper<Record> {

    @Select("select * from record " +
            "where topic = #{topic} " +
            "order by offset desc limit 1")
    Record selectOneByTopicOrderByOffsetDesc(String topic);

    @Select("select * from record " +
            "where topic = #{topic} and offset > #{offset} " +
            "order by offset asc limit 1")
    Record selectOneByTopicAndOffsetOrderByOffsetAsc(@Param("topic") String topic,
                                                     @Param("offset") long offset);

    List<Record> selectByTopicAndOffsetOrderByOffsetAscLimit(@Param("topic") String topic,
                                                             @Param("offset") long offset,
                                                             @Param("limit") Integer limit);
}
