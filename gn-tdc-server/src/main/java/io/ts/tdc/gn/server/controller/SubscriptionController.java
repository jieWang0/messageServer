package io.ts.tdc.gn.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.ts.mangix.web.converter.ConverterContext;
import io.ts.tdc.gn.common.transport.TResult;
import io.ts.tdc.gn.common.transport.TSubscription;
import io.ts.tdc.gn.model.Subscription;
import io.ts.tdc.gn.server.converter.SubscriptionConverter;
import io.ts.tdc.gn.service.SubscriptionService;
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
@RequestMapping(ApiConstants.VERSION)
public class SubscriptionController {


}
