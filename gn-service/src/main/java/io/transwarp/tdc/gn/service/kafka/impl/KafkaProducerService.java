package io.transwarp.tdc.gn.service.kafka.impl;


import io.transwarp.tdc.gn.common.KafkaConfigUtils;
import io.transwarp.tdc.gn.common.exception.ErrorCode;
import io.transwarp.tdc.gn.common.exception.GNException;
import io.transwarp.tdc.gn.common.transport.TResult;
import io.transwarp.tdc.gn.repository.impl.KafkaProduceDao;
import io.transwarp.tdc.gn.service.kafka.KafkaNotificationService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class KafkaProducerService extends KafkaNotificationService {

    @Autowired
    private KafkaProduceDao notificationDao;

    @Autowired
    KafkaConfigUtils kafkaConfigUtils;

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Override
    public void send(String topic,String key,String message) throws GNException{
        Properties properties = kafkaConfigUtils.getKafkaConfig();
        Producer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        try {
            producer.send(record).get();
        } catch (Exception e) {
            logger.error("KafkaProducerService.send:failed to send message",e);
            throw new GNException(ErrorCode.MESSAGE_SEND_INTERNAL_ERROR,e.getMessage());
        }finally {
            producer.close();
        }
    }

    @Override
    public TResult ensureSend(String topic, String key, String message) {

        if(!checkTopic(topic))
            return TResult.fail("topic 不合法");

        Properties properties = kafkaConfigUtils.getKafkaConfig();
        Producer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        try {
            producer.send(record).get();
        } catch (Exception e) {
            logger.error("ensureSend:failed to send message,retrying",e);
            notificationDao.saveFailedProduce(topic,message);
        }finally {
            producer.close();
        }
        return TResult.success("success");
    }

    /**
     * desc:判断topic是否合法
     * */
    public boolean checkTopic(String topic) {
        Properties properties = kafkaConfigUtils.getKafkaConfig();
        Consumer<String,String> consumer = new KafkaConsumer<>(properties);
        Map<String,List<PartitionInfo>> listTopics = consumer.listTopics();
        return listTopics.containsKey(topic);
    }


}
