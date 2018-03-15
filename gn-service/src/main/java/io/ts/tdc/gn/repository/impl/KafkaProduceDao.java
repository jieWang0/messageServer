package io.ts.tdc.gn.repository.impl;

import io.ts.tdc.gn.mapper.KafkaProduceMapper;
import io.ts.tdc.gn.model.KafkaProduceEntity;
import io.ts.tdc.gn.repository.IKafkaProduceDao;
import io.ts.tdc.gn.service.kafka.impl.KafkaProducerService;
import io.ts.tdc.gn.mapper.KafkaProduceMapper;
import io.ts.tdc.gn.model.KafkaProduceEntity;
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
    public void saveFailedProduce(String id,String topic ,String message,Long createTime) {
        kafkaProduceMapper.saveFailedProduce(id,topic,message,createTime);
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

}
