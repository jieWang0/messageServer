package io.transwarp.tdc.gn.service.kafka.impl;

import io.transwarp.tdc.gn.common.NotificationStorageType;
import io.transwarp.tdc.gn.model.MetaInfo;
import io.transwarp.tdc.gn.service.kafka.KafkaConfigUtils;
import io.transwarp.tdc.gn.service.kafka.KafkaProducerConfigInfo;
import io.transwarp.tdc.gn.common.NotificationProducerRecord;
import io.transwarp.tdc.gn.common.exception.ErrorCode;
import io.transwarp.tdc.gn.common.exception.GNException;
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
    private KafkaProduceDao kafkaProduceDao;

    @Autowired
    KafkaProducerConfigInfo kafkaProducerConfigInfo;

    @Autowired
    KafkaConfigUtils kafkaConfigUtils;

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Override
    public MetaInfo getMetaInfo() {
        MetaInfo metaInfo =  new MetaInfo();
        metaInfo.setType(NotificationStorageType.KAFKA.toString());
        metaInfo.setUrl(kafkaProducerConfigInfo.getBootstrapServers());
        return metaInfo;
    }

    @Override
    public <T> void send(NotificationProducerRecord<T> record,boolean ensureSend) {

        if(!checkTopic(record.topic()))
            throw new GNException(ErrorCode.INVALID_TOPIC_ERROR,"topic不合法");

        Producer<String, String> producer = kafkaConfigUtils.getKafkaProducer();
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>(record.topic(),record.payload().toString());
        try {
            producer.send(producerRecord).get();
        } catch (Exception e) {
            logger.error("KafkaProducerService.send:failed to send message",e);
            if(ensureSend) {
                kafkaProduceDao.saveFailedProduce(record.guid(),record.topic(),record.payload().toString(),record.createTime());
            }else {
                throw new GNException(ErrorCode.SEND_ERROR,"KafkaProducerService.send:failed to send message");
            }
        }
        // producer.close();

    }

    public void autoRetryProduce(Producer<String,String> producer, String recordId, ProducerRecord<String, String> record) {

        RecordMetadata recordMetadata = null;
        try {
            recordMetadata =producer.send(record).get();
        } catch (Exception e) {
            logger.error("KafkaProduceDao.autoRetryProduce:failed to produce message,retrying",e);
        }
        if(recordMetadata!=null) {
            kafkaProduceDao.deleteFailedProduce(recordId);
        }

    }
    /**
     * desc:判断topic是否合法
     * */
    private boolean checkTopic(String topic) {
        Properties properties = kafkaConfigUtils.getKafkaConsumerConfig();
        Consumer<String,String> consumer = new KafkaConsumer<>(properties);
        Map<String,List<PartitionInfo>> listTopics = consumer.listTopics();
        return listTopics.containsKey(topic);
    }

    @Override
    public void close() {

    }
}
