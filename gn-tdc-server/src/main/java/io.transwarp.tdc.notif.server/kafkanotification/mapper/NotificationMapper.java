package io.transwarp.tdc.notif.server.kafkanotification.mapper;

import io.transwarp.tdc.notif.server.kafkanotification.entity.NotificationEntity;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface NotificationMapper {

    @Insert("INSERT INTO message_remain(topic,message) VALUES(#{0},#{1})")
    void saveMessage(String topic,String message);

    @Select("SELECT * FROM message_remain")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "topic",  column = "topic"),
            @Result(property = "message", column = "message")
    })
    List<NotificationEntity> getMessage();

    @Delete("DELETE FROM message_remain WHERE id = #{id}")
    void deleteMessage(Integer id);
}
