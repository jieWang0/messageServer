package io.transwarp.tdc.gn.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.transwarp.tdc.gn.common.exception.ErrorCode;
import io.transwarp.tdc.gn.common.exception.GNException;
import io.transwarp.tdc.gn.common.transport.TPayload;
import io.transwarp.tdc.gn.common.transport.TResult;
import io.transwarp.tdc.gn.service.ProducerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 18-2-5 created by zado
 */
@Api(value = "消息发送", description = "消息发送相关操作")
@RestController
@RequestMapping(ApiConstants.VERSION + "/producers")
public class ProducerController {

    @Resource
    private ProducerService producerService;

    @ApiOperation(value = "发送消息", notes = "body为string")
    @RequestMapping(value = "/{topic}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult produce(
            @PathVariable String topic,
            @RequestBody TPayload payload,
            @RequestParam(value = "ensureSuccess", required = false, defaultValue = "true") boolean ensureSuccess) {

        producerService.produce(topic, payload.getPayload());
        return TResult.success("success to send record");
    }
}
