package io.ts.tdc.gn.common.transport;

/**
 * 操作结果的消息体
 *
 * 18-2-1 created by zado
 */
public class TResult {

    private String result;

    private String message;

    public TResult() {
    }

    public static TResult success(String message) {
        TResult result = new TResult();
        result.setResult("success");
        result.setMessage(message);

        return result;
    }

    public static TResult fail(String message) {
        TResult result = new TResult();
        result.setResult("fail");
        result.setMessage(message);

        return result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
