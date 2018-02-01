package io.transwarp.tdc.notif.server.kafkanotification.dao.impl;

import io.transwarp.tdc.notif.server.kafkanotification.dao.INotificationDao;
import io.transwarp.tdc.notif.server.kafkanotification.entity.NotificationEntity;
import io.transwarp.tdc.notif.server.kafkanotification.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("notificationDao'")
public class NotificationDao implements INotificationDao{

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public void saveMessage(String topic ,String message) {
        notificationMapper.saveMessage(topic,message);
    }

    @Override
    public List<NotificationEntity> getMessage() {
        List<NotificationEntity> list = new ArrayList<>();
        list =notificationMapper.getMessage();
        return list;
    }

    @Override
    public void deleteMessage(Integer id) {
        notificationMapper.deleteMessage(id);
    }
}
