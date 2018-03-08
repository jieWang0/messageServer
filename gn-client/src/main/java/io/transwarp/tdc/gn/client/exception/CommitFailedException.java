package io.transwarp.tdc.gn.client.exception;

public class CommitFailedException extends RuntimeException {
    public CommitFailedException() {
    }

    public CommitFailedException(String message) {
        super(message);
    }

    public CommitFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommitFailedException(Throwable cause) {
        super(cause);
    }

    public CommitFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
