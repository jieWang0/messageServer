package com.example.kafkatest.kafkanotification.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Configuration
@EnableConfigurationProperties(KafkaConfigBean.class)
public class KafkaConfigUtils {

    @Autowired
    private KafkaConfigBean kafkaConfigBean;

    public Properties getKafkaConfig(){
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaConfigBean.getBootstrapServers());
        props.put("acks", kafkaConfigBean.getAcks());
        props.put("retries", kafkaConfigBean.getRetries());
        props.put("batch.size", kafkaConfigBean.getBatchSize());
        props.put("key.serializer", kafkaConfigBean.getKeySerializer());
        props.put("value.serializer", kafkaConfigBean.getValueSerializer());
        return props;
    }
}
