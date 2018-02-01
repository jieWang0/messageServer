package io.transwarp.tdc.notif.server.kafkanotification.rest;
import io.transwarp.tdc.notif.server.kafkanotification.service.impl.NgProducerService;
import io.transwarp.tdc.notif.server.kafkanotification.utils.KafkaConfigUtils;
import io.transwarp.tdc.notif.server.kafkanotification.utils.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
@RequestMapping("/notice")
public class NgProducerController {

    @Autowired
    private KafkaConfigUtils kafkaConfigUtils;

    @Autowired
    private NgProducerService ngProducerService;

    @RequestMapping("/produce")
    public ResponseInfo produce(@RequestParam(name = "topic") String topic,
                                @RequestParam(name = "key" ,required = false) String key,
                                @RequestParam(name = "message") String message) {
        ResponseInfo responseInfo = new ResponseInfo();
        Properties props = kafkaConfigUtils.getKafkaConfig();
        ngProducerService.produce(props,topic,key,message);
        return responseInfo;
    }

    @RequestMapping("/tryProduce")
    public ResponseInfo tryProduce(@RequestParam(value = "topic") String topic,
                                   @RequestParam(name = "key" ,required = false) String key,
                                @RequestParam(value = "message") String message) {
        ResponseInfo responseInfo = new ResponseInfo();
        Properties props = kafkaConfigUtils.getKafkaConfig();
        ngProducerService.tryProduce(props,topic,key,message);
        return responseInfo;
    }
}
