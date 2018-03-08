package io.transwarp.tdc.gn.common.exception;

/**
 * 18-2-5 created by zado
 */
class ErrorFormatter {

    public static String format(ErrorCode error, String message) {
        return "Error[" + error.getCode() + "]" + error.name()
            + (message != null && !message.isEmpty() ? ": " + message : "");
    }
}
