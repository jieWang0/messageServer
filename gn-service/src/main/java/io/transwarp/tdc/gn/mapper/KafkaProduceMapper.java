package com.example.kafkatest.kafkanotification.mapper;

import io.transwarp.tdc.notif.server.kafkanotification.entity.NotificationEntity;

import java.util.List;

public interface NotificationMapper {

    void saveFailedProduce(String topic, String message);

    List<NotificationEntity> getFailedProduce();

    void deleteFailedProduce(Long id);
}
