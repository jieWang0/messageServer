package io.transwarp.tdc.gn.repository;

import io.transwarp.tdc.gn.model.KafkaProduceEntity;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;

public interface IKafkaProduceDao {
    void saveFailedProduce(String id,String topic, String message,Long createTime);
    List<KafkaProduceEntity> getFailedProduce();
    void deleteFailedProduce(String id);
}
