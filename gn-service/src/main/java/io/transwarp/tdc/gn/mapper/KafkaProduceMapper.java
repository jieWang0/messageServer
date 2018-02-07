package io.transwarp.tdc.gn.mapper;


import io.transwarp.tdc.gn.model.KafkaProduceEntity;

import java.util.List;

public interface KafkaProduceMapper {

    void saveFailedProduce(String topic, String message);

    List<KafkaProduceEntity> getFailedProduce();

    void deleteFailedProduce(Long id);
}
