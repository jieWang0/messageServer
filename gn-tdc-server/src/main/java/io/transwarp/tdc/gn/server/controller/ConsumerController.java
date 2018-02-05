package io.transwarp.tdc.gn.server.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 只有database的实现才会有提供api
 * 18-2-5 created by zado
 */
@Api(value = "消费者", description = "消息消费相关操作")
@RestController
@RequestMapping(ApiConstants.VERSION + "/consumers")
public class ConsumerController {
}
