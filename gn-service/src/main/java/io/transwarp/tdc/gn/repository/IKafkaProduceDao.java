package com.example.kafkatest.kafkanotification.dao;

import io.transwarp.tdc.notif.server.kafkanotification.entity.NotificationEntity;

import java.util.List;

public interface INotificationDao {
    public void saveFailedProduce(String topic, String message);
    public List<NotificationEntity> getFailedProduce();
    public void deleteFailedProduce(Long id);
}
