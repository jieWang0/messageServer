package io.ts.tdc.gn.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.ts.mangix.web.converter.ConverterContext;
import io.ts.tdc.gn.common.transport.TMetaInfo;
import io.ts.tdc.gn.server.converter.MetaInfoConverter;
import io.ts.tdc.gn.service.MetaInfoService;
import io.ts.tdc.gn.service.MetaInfoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 18-2-5 created by zado
 */
@Api(value = "服务基本信息", description = "基本信息相关操作")
@RestController
@RequestMapping(ApiConstants.VERSION)
public class MetaInfoController {

    @Resource
    private MetaInfoService metaInfoService;

    @ApiOperation(value = "获取基本信息", notes = "返回服务方式、地址等")
    @RequestMapping(value = "/metaInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TMetaInfo getMetaInfo() {
        return ConverterContext.getConverter(MetaInfoConverter.class).convert(metaInfoService.getMetaInfo());
    }
}
