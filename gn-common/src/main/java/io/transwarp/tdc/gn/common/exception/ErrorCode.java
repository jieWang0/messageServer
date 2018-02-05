package io.transwarp.tdc.gn.common.exception;

/**
 * 18-2-5 created by zado
 */
public enum ErrorCode {
    METHOD_NOT_SUPPORTED(100001);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
