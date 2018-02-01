package io.transwarp.tdc.notif.server.kafkanotification.dao;

import io.transwarp.tdc.notif.server.kafkanotification.entity.NotificationEntity;

import java.util.List;

public interface INotificationDao {
    public void saveMessage(String topic,String message);
    public List<NotificationEntity> getMessage();
    public void deleteMessage(Integer id);
}
