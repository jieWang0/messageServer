package io.ts.tdc.gn.mapper;


import io.ts.tdc.gn.model.KafkaProduceEntity;

import java.util.List;

public interface KafkaProduceMapper {

    void saveFailedProduce(String id, String topic, String message,long createTime);

    List<KafkaProduceEntity> getFailedProduce();

    void deleteFailedProduce(String id);
}
