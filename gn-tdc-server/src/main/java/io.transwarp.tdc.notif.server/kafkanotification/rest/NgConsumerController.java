package io.transwarp.tdc.notif.server.kafkanotification.rest;
import io.transwarp.tdc.notif.server.kafkanotification.service.impl.NgConsumerService;
import io.transwarp.tdc.notif.server.kafkanotification.utils.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/notice")
public class NgConsumerController {
    @Autowired
    private NgConsumerService ngConsumerService;

    @RequestMapping("/consume")
    public ResponseInfo consume() {
        ResponseInfo responseInfo = new ResponseInfo();
        Properties props = ngConsumerService.consumeDetail();
        Map<String, String> result = new HashMap<String, String>((Map)props);
        responseInfo.setResult(result);
        return responseInfo;
    }
}
