package io.transwarp.tdc.gn.server.controller;

import io.transwarp.tdc.gn.common.exception.GNException;
import io.transwarp.tdc.gn.common.transport.TResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常处理
 * 18-2-5 created by zado
 */
@ControllerAdvice
public class ExceptionAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvise.class);

    @ExceptionHandler(GNException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public TResult handleGNException(GNException e) {
        LOGGER.error("GNException:", e);
        return TResult.fail(e.getMessage());
    }
}
