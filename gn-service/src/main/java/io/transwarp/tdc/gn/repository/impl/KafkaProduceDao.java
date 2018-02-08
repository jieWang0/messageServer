package io.transwarp.tdc.gn.repository.impl;

import io.transwarp.tdc.gn.mapper.KafkaProduceMapper;
import io.transwarp.tdc.gn.model.KafkaProduceEntity;
import io.transwarp.tdc.gn.repository.IKafkaProduceDao;
import io.transwarp.tdc.gn.service.kafka.impl.KafkaProducerService;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class KafkaProduceDao  implements IKafkaProduceDao {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaProduceMapper kafkaProduceMapper;

    @Override
    public void saveFailedProduce(String id,String topic ,String message) {
        kafkaProduceMapper.saveFailedProduce(id,topic,message);
    }

    @Override
    public List<KafkaProduceEntity> getFailedProduce() {
        List<KafkaProduceEntity> list = new ArrayList<>();
        list =kafkaProduceMapper.getFailedProduce();
        return list;
    }

    @Override
    public void deleteFailedProduce(String id) {
        kafkaProduceMapper.deleteFailedProduce(id);
    }

    @Override
    public void autoRetryProduce(Producer<String,String> producer, String recordId, ProducerRecord<String, String> record) {

        RecordMetadata recordMetadata = null;
        try {
             recordMetadata =producer.send(record).get();
        } catch (Exception e) {
            logger.error("KafkaProduceDao.autoRetryProduce:failed to produce message,retrying",e);
        }
        if(recordMetadata!=null) {
            deleteFailedProduce(recordId);
        }

    }
}
