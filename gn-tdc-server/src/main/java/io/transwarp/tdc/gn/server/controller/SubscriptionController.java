package io.transwarp.tdc.gn.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.transwarp.mangix.web.converter.ConverterContext;
import io.transwarp.tdc.gn.common.transport.TResult;
import io.transwarp.tdc.gn.common.transport.TSubscription;
import io.transwarp.tdc.gn.model.Subscription;
import io.transwarp.tdc.gn.server.converter.SubscriptionConverter;
import io.transwarp.tdc.gn.service.SubscriptionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 18-2-7 created by zado
 */
@Api(value = "消息订阅", description = "订阅/取消订阅消息主题")
@RestController
@RequestMapping(ApiConstants.VERSION + "/subscriptions")
public class SubscriptionController {

    @Resource
    private SubscriptionService subscriptionService;

    @ApiOperation(value = "订阅一个主题", notes = "主题必须存在")
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult subscribe(
            @RequestParam(value = "subscriber") String subscriber,
            @RequestParam(value = "topic") String topic) {

        subscriptionService.subscribe(subscriber, topic);
        return TResult.success("Success to subscribe topic");
    }

    @ApiOperation(value = "取消订阅一个主题")
    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult unsubscribe(
            @RequestParam(value = "subscriber") String subscriber,
            @RequestParam(value = "topic") String topic) {

        subscriptionService.unsubscribe(subscriber, topic);
        return TResult.success("Success to unsubscribe topic");
    }

    @ApiOperation(value = "查看某个用户的订阅主题", notes = "返回订阅列表")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TSubscription> listSubscriptions(
            @RequestParam(value = "subscriber") String subscriber) {

        return ConverterContext.getConverter(SubscriptionConverter.class)
                .convert(subscriptionService.listSubscriptions(subscriber));
    }
}
