package io.transwarp.tdc.gn.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.transwarp.mangix.web.converter.ConverterContext;
import io.transwarp.tdc.gn.common.transport.TConsumerOffset;
import io.transwarp.tdc.gn.common.transport.TOffsetCommit;
import io.transwarp.tdc.gn.common.transport.TRecord;
import io.transwarp.tdc.gn.common.transport.TResult;
import io.transwarp.tdc.gn.model.ConsumerOffset;
import io.transwarp.tdc.gn.model.Record;
import io.transwarp.tdc.gn.server.converter.ConsumerOffsetConverter;
import io.transwarp.tdc.gn.server.converter.RecordConverter;
import io.transwarp.tdc.gn.service.ConsumerOffsetService;
import io.transwarp.tdc.gn.service.ConsumerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 只有database的实现才会有提供api
 * 18-2-5 created by zado
 */
@Api(value = "消费者", description = "消息消费相关操作")
@RestController
@RequestMapping(ApiConstants.VERSION + "/consumers")
public class ConsumerController {

    @Resource
    private ConsumerOffsetService consumerOffsetService;

    @Resource
    private ConsumerService consumerService;

    @ApiOperation(value = "查看当前消费的位置", notes = "无法查阅没有订阅的主题")
    @RequestMapping(value = "/offsets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TConsumerOffset fetchOffset(
            @RequestParam("topic") String topic,
            @RequestParam("subscriber") String subscriber) {

        return ConverterContext.getConverter(ConsumerOffsetConverter.class)
                .convert(consumerOffsetService.fetch(topic, subscriber));
    }

    @ApiOperation(value = "提交消费位置", notes = "offset必须位于当前记录之后")
    @RequestMapping(value = "/offsets/commit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult commitOffset(
            @RequestBody TOffsetCommit offsetCommit) {

        consumerOffsetService.commit(offsetCommit.getTopic(), offsetCommit.getSubscriber(), offsetCommit.getOffset());
        return TResult.success("Success to commit offset");
    }

    @ApiOperation(value = "消费一条消息", notes = "默认会自动更新offset")
    @RequestMapping(value = "/records/once", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TRecord consumeOne(
            @RequestParam(value = "topic") String topic,
            @RequestParam(value = "subscriber") String subscriber,
            @RequestParam(value = "autoCommit", required = false, defaultValue = "true") Boolean autoCommit) {

        return ConverterContext.getConverter(RecordConverter.class)
                .convert(consumerService.consume(topic, subscriber, autoCommit));
    }

    @ApiOperation(value = "批量消费消息", notes = "当count不设置的时候消费剩下所有的")
    @RequestMapping(value = "/records/batch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TRecord> consumeBatch(
            @RequestParam(value = "topic") String topic,
            @RequestParam(value = "subscriber") String subscriber,
            @RequestParam(value = "count", required = false) Integer count,
            @RequestParam(value = "autoCommit", required = false, defaultValue = "true") Boolean autoCommit) {

        return ConverterContext.getConverter(RecordConverter.class)
                .convert(consumerService.consumeBatch(topic, subscriber, count, autoCommit));
    }
}
