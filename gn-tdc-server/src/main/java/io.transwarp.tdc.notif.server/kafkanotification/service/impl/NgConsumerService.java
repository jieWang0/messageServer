package io.transwarp.tdc.notif.server.kafkanotification.service.impl;

import io.transwarp.tdc.notif.server.kafkanotification.service.INgConsumerService;
import io.transwarp.tdc.notif.server.kafkanotification.utils.KafkaConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class NgConsumerService implements INgConsumerService {
    @Autowired
    private KafkaConfigUtils kafkaConfigUtils;

    @Override
    public Properties consumeDetail() {
        Properties props = kafkaConfigUtils.getKafkaConfig();
        return props;
    }
}
