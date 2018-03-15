package io.transwarp.tdc.gn.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.transwarp.mangix.web.converter.ConverterContext;
import io.transwarp.tdc.gn.common.transport.*;
import io.transwarp.tdc.gn.model.ConsumerOffset;
import io.transwarp.tdc.gn.model.Record;
import io.transwarp.tdc.gn.server.converter.ConsumerOffsetConverter;
import io.transwarp.tdc.gn.server.converter.RecordConverter;
import io.transwarp.tdc.gn.server.converter.RecordsConverter;
import io.transwarp.tdc.gn.server.converter.SubscriptionConverter;
import io.transwarp.tdc.gn.service.ConsumerOffsetService;
import io.transwarp.tdc.gn.service.ConsumerService;
import io.transwarp.tdc.gn.service.SubscriptionService;
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
@RequestMapping(ApiConstants.VERSION)
public class ConsumerController {

    @Resource
    private ConsumerOffsetService consumerOffsetService;

    @Resource
    private ConsumerService consumerService;

    @Resource
    private SubscriptionService subscriptionService;

    @ApiOperation(value = "查看当前消费的位置", notes = "无法查阅没有订阅的主题")
    @RequestMapping(value = "/consumers/{subscriber}/topics/{topic}/offsets",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TConsumerOffset fetchOffset(
            @PathVariable("subscriber") String subscriber,
            @PathVariable("topic") String topic) {
        return ConverterContext.getConverter(ConsumerOffsetConverter.class)
                .convert(consumerOffsetService.fetch(topic, subscriber));
    }

    // todo
    @ApiOperation(value = "提交消费位置", notes = "offset必须位于当前记录之后")
    @RequestMapping(value = "/consumers/{subscriber}/topics/{topic}/commitments",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult commitOffset(@PathVariable String subscriber,
                                @PathVariable String topic,
                                @RequestBody TOffsetCommit offsetCommit) {
        consumerOffsetService.commit(topic, subscriber, offsetCommit.getOffset());
        return TResult.success("Success to commit offset");
    }

    @ApiOperation(value = "批量消费消息", notes = "当count不设置的时候消费剩下所有的")
    @RequestMapping(value = "/consumers/{subscriber}/topics/{topic}/records", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TRecords consume(
            @PathVariable(value = "subscriber") String subscriber,
            @PathVariable(value = "topic") String topic,
            @RequestParam(value = "count", required = false) Integer count,
            @RequestParam(value = "user", required = false) String user) {
        return ConverterContext.getConverter(RecordsConverter.class)
                .convert(consumerService.consumeBatch(topic, subscriber, count, user));
    }

    // todo
    @ApiOperation(value = "心跳请求", notes = "记录消费者活跃时间")
    @RequestMapping(value = "/consumers/{subscriber}/topics/{topic}/heartbeats", method = RequestMethod.GET)
    public void heartbeat(@PathVariable("subscriber") String subscriber,
                          @PathVariable("topic") String topic,
                          @RequestParam("user") String user) {
        consumerService.heartbeat(topic, subscriber, user);
    }

    @ApiOperation(value = "订阅一个主题", notes = "主题必须存在")
    @RequestMapping(value = "/consumers/{subscriber}/topics/{topic}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult subscribe(@PathVariable(value = "subscriber") String subscriber,
                             @PathVariable(value = "topic") String topic,
                             @RequestParam("user") String user) {
        subscriptionService.subscribe(subscriber, topic, user);
        return TResult.success("Success to subscribe topic");
    }

    @ApiOperation(value = "取消订阅一个主题")
    @RequestMapping(value = "/consumers/{subscriber}/topics/{topic}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult unsubscribe(
            @PathVariable(value = "subscriber") String subscriber,
            @PathVariable(value = "topic") String topic) {
        subscriptionService.unsubscribe(subscriber, topic);
        return TResult.success("Success to unsubscribe topic");
    }

    @ApiOperation(value = "查看某个用户的订阅主题", notes = "返回订阅列表")
    @RequestMapping(value = "/consumers/{subscriber}/topics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TSubscription> listSubscriptions(
            @RequestParam(value = "subscriber") String subscriber) {
        return ConverterContext.getConverter(SubscriptionConverter.class)
                .convert(subscriptionService.listSubscriptions(subscriber));
    }

}
