package io.transwarp.tdc.notif.server.kafkanotification.utils;

import io.transwarp.tdc.notif.server.kafkanotification.dao.impl.NotificationDao;
import io.transwarp.tdc.notif.server.kafkanotification.service.impl.NgProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class ProduceCommand implements Runnable{

    @Autowired
    private NotificationDao notificationDao;

    private static final Logger logger = LoggerFactory.getLogger(NgProducerService.class);

    private String topic;
    private String key;
    private String message;
    private Properties properties;


    @Override
    public void run() {
        Producer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, message);
        try {
            producer.send(record).get();
        } catch (Exception e) {
            logger.error("ProduceCommand:failed to produce message,retrying",e);
            notificationDao.saveMessage(topic,message);
        }
        producer.close();

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
