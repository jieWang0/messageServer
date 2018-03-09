package io.transwarp.tdc.gn.client.exception;

/**
 * this exception represents a shutdown action,
 * which can be absorbed silently to exit a consuming loop
 */
public class ShutdownException extends RuntimeException {

    public ShutdownException() {
    }

    public ShutdownException(String message) {
        super(message);
    }

    public ShutdownException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShutdownException(Throwable cause) {
        super(cause);
    }

    public ShutdownException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
