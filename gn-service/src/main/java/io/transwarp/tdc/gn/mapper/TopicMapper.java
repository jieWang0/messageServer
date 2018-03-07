package io.transwarp.tdc.gn.mapper;

import io.transwarp.mangix.web.mybatis.BaseMapper;
import io.transwarp.tdc.gn.model.Topic;
import org.apache.ibatis.annotations.Select;

/**
 * 18-2-6 created by zado
 */
public interface TopicMapper extends BaseMapper<Topic> {

    @Select("select * from topic where name = #{name}")
    Topic selectOneByName(String name);
}
