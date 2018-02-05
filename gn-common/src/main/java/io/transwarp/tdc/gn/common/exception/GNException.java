package io.transwarp.tdc.gn.common.exception;

/**
 * generic notification的基本异常类型
 *
 * 18-2-5 created by zado
 */
public class GNException extends RuntimeException {

    private ErrorCode errorCode;

    public GNException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public GNException(ErrorCode errorCode, String message) {
        super(ErrorFormatter.format(errorCode, message));
        this.errorCode = errorCode;
    }

    public GNException(ErrorCode errorCode, String message, Throwable cause) {
        super(ErrorFormatter.format(errorCode, message), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
