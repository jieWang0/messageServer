package io.transwarp.tdc.gn.repository;

import io.transwarp.tdc.gn.model.KafkaProduceEntity;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;

public interface IKafkaProduceDao {
    void saveFailedProduce(String topic, String message);
    List<KafkaProduceEntity> getFailedProduce();
    void deleteFailedProduce(Long id);
    void autoRetryProduce(Producer<String,String> producer, Long recordId, ProducerRecord<String, String> record);
}
