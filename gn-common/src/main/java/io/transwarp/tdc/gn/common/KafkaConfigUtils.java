package io.transwarp.tdc.gn.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Configuration
@EnableConfigurationProperties(KafkaProducerConfigInfo.class)
public class KafkaConfigUtils {

    @Autowired
    private KafkaProducerConfigInfo kafkaProducerConfigInfo;

    public Properties getKafkaProducerConfig(){
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProducerConfigInfo.getBootstrapServers());
        props.put("acks", kafkaProducerConfigInfo.getAcks());
        props.put("retries", kafkaProducerConfigInfo.getRetries());
        props.put("batch.size", kafkaProducerConfigInfo.getBatchSize());
        props.put("key.serializer", kafkaProducerConfigInfo.getKeySerializer());
        props.put("value.serializer", kafkaProducerConfigInfo.getValueSerializer());
        return props;
    }
}
