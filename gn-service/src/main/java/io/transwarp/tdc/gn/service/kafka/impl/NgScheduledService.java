package io.transwarp.tdc.gn.service.kafka.impl;

import io.transwarp.tdc.gn.service.kafka.KafkaConfigUtils;
import io.transwarp.tdc.gn.service.kafka.KafkaProducerConfigInfo;
import io.transwarp.tdc.gn.model.KafkaProduceEntity;
import io.transwarp.tdc.gn.repository.impl.KafkaProduceDao;
import io.transwarp.tdc.gn.service.condition.KafkaImpl;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
@Configuration
@EnableConfigurationProperties(KafkaProducerConfigInfo.class)
@Conditional(KafkaImpl.class)
public class NgScheduledService {
    @Autowired
    private KafkaProduceDao kafkaProduceDao;

    @Autowired
    private KafkaConfigUtils kafkaConfigUtils;

    /**
     * desc：将之前produce失败后存储到DB的数据重新produce
     * */
    @Scheduled(cron = "0 0 */1 * * ? ")
    public void retryFailedProduce() {
        Properties properties = kafkaConfigUtils.getKafkaProducerConfig();
        Producer<String, String> producer = new KafkaProducer<>(properties);
        List<KafkaProduceEntity> messageList = kafkaProduceDao.getFailedProduce();
        messageList.forEach(entity->{
            ProducerRecord<String, String> record = new ProducerRecord<>(entity.getTopic(), entity.getMessage());
            kafkaProduceDao.autoRetryProduce(producer,entity.getId(),record);
        });
    }
}
