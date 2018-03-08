package io.transwarp.tdc.gn.mapper;

import io.transwarp.mangix.web.mybatis.BaseMapper;
import io.transwarp.tdc.gn.model.Subscription;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 18-2-6 created by zado
 */
public interface SubscriptionMapper extends BaseMapper<Subscription> {

    @Select("select * from subscription " +
            "where subscriber = #{subscriber} and topic = #{topic}")
    Subscription selectOneBySubscriberAndTopic(@Param("subscriber") String subscriber,
                                               @Param("topic") String topic);

    @Select("select * from subscription " +
            "where subscriber = #{subscriber}")
    List<Subscription> selectBySubscriber(String subscriber);

    @Delete("delete from subscription " +
            "where subscriber = #{subscriber} and topic = #{topic}")
    void deleteBySubscriberAndTopic(@Param("subscriber") String subscriber,
                                    @Param("topic") String topic);
}
