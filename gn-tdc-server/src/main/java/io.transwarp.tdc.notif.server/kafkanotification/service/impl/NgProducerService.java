package io.transwarp.tdc.notif.server.kafkanotification.service.impl;

import io.transwarp.tdc.notif.server.kafkanotification.service.INgProducerService;
import io.transwarp.tdc.notif.server.kafkanotification.utils.NgExecutorService;
import io.transwarp.tdc.notif.server.kafkanotification.utils.ProduceCommand;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
public class NgProducerService implements INgProducerService {

    @Autowired
    private ProduceCommand produceCommand;

    private static final Logger logger = LoggerFactory.getLogger(NgProducerService.class);

    @Override
    public void produce(Properties properties,String topic,String key,String message) {
        Producer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        producer.send(record,new produceCallBack());
        producer.close();
    }

    @Override
    public void tryProduce(Properties properties, String topic,String key,String message) {
        produceCommand.setMessage(message);
        produceCommand.setProperties(properties);
        produceCommand.setTopic(topic);
        produceCommand.setKey(key);
        NgExecutorService.ngExecutorService.execute(produceCommand);
    }


    private class produceCallBack implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if(e!=null) {
                logger.error("NgProducerService.produce:failed to produce message",e);
            }
        }
    }

}
