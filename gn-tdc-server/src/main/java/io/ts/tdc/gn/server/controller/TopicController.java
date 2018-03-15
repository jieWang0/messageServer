package io.ts.tdc.gn.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.ts.mangix.web.converter.ConverterContext;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;
import io.ts.tdc.gn.common.transport.TResult;
import io.ts.tdc.gn.common.transport.TTopic;
import io.ts.tdc.gn.model.Topic;
import io.ts.tdc.gn.server.converter.TopicConverter;
import io.ts.tdc.gn.service.TopicService;
import io.ts.tdc.gn.model.Topic;
import io.ts.tdc.gn.service.TopicService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 18-2-6 created by zado
 */
@Api(value = "消息主题", description = "消息主题操作(查询)")
@RestController
@RequestMapping(ApiConstants.VERSION + "/topics")
public class TopicController {

    @Resource
    private TopicService topicService;

    @ApiOperation(value = "查看主题", notes = "根据名称查看")
    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TTopic getTopic(@PathVariable String name) {
        Topic topic = topicService.getTopic(name);
        if (topic == null) {
            throw new GNException(ErrorCode.TOPIC_NOT_FOUND, String.format("Topic[name=%s] is not found", name));
        }

        return ConverterContext.getConverter(TopicConverter.class).convert(topic);
    }

    @ApiOperation(value = "查看主题列表", notes = "显示所有的")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TTopic> listTopics() {
        return ConverterContext.getConverter(TopicConverter.class).convert(topicService.listTopics());
    }

    @ApiOperation(value = "测试使用的创建topic接口", notes = "FBI WARNING")
    @RequestMapping(value = "/mock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult mockTopics() {
        topicService.createTopic();
        return TResult.success("mock topics are ready");
    }
}
