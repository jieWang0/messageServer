package com.example.kafkatest.kafkanotification.dao.impl;

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
    public void saveFailedProduce(String topic ,String message) {
        notificationMapper.saveFailedProduce(topic,message);
    }

    @Override
    public List<NotificationEntity> getFailedProduce() {
        List<NotificationEntity> list = new ArrayList<>();
        list =notificationMapper.getFailedProduce();
        return list;
    }

    @Override
    public void deleteFailedProduce(Long id) {
        notificationMapper.deleteFailedProduce(id);
    }
}
