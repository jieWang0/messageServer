package io.ts.tdc.gn.service.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaConfigUtils {

    @Autowired
    KafkaProducerConfigInfo kafkaProducerConfigInfo;

    @Autowired
    KafkaConsumerConfigInfo kafkaConsumerConfigInfo;

    private  KafkaProducer<String,String> kafkaProducer ;

    public KafkaProducer<String,String> getKafkaProducer(){
        if(this.kafkaProducer == null) {
            this.kafkaProducer = new KafkaProducer<>(getKafkaProducerConfig());
        }
        return this.kafkaProducer;
    }

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

    public Properties getKafkaConsumerConfig(){
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaConsumerConfigInfo.getBootstrapServers());
        props.put("enable.auto.commit", kafkaConsumerConfigInfo.isAutoCommit());
        props.put("group.id", kafkaConsumerConfigInfo.getGroup());
        props.put("key.deserializer", kafkaConsumerConfigInfo.getKeyDeserializer());
        props.put("value.deserializer", kafkaConsumerConfigInfo.getValueDeserializer());
        return props;
    }
}
