package io.transwarp.tdc.gn.mapper;

import io.transwarp.mangix.web.mybatis.BaseMapper;
import io.transwarp.tdc.gn.model.ConsumerOffset;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 18-2-6 created by zado
 */
public interface ConsumerOffsetMapper extends BaseMapper<ConsumerOffset> {

    @Select("select * from consumer_offset " +
            "where topic = #{topic} and subscriber = #{subscriber}")
    ConsumerOffset selectOneByTopicAndSubscribe(@Param("topic") String topic,
                                                @Param("subscriber") String subscriber);

    @Update("update consumer_offset " +
            "set current_offset = #{currentOffset}, commit_time = #{commitTime} " +
            "where topic = #{topic} and subscriber = #{subscriber}")
    void updateByTopicAndSubscriber(ConsumerOffset consumerOffset);

    @Delete("delete from consumer_offset " +
            "where subscriber = #{subscriber} and topic = #{topic}")
    void deleteByTopicAndSubscriber(@Param("topic") String topic,
                                    @Param("subscriber") String subscriber);
}
