package io.transwarp.tdc.gn.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.transwarp.tdc.gn.common.exception.ErrorCode;
import io.transwarp.tdc.gn.common.exception.GNException;
import io.transwarp.tdc.gn.common.transport.TMetaInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 18-2-5 created by zado
 */
@Api(value = "服务基本信息", description = "基本信息相关操作")
@RestController(ApiConstants.VERSION)
public class MetaInfoController {

    @ApiOperation(value = "获取基本信息", notes = "返回服务方式、地址等")
    @RequestMapping(value = "/metaInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TMetaInfo getMetaInfo() {
        throw new GNException(ErrorCode.METHOD_NOT_SUPPORTED);
    }
}
