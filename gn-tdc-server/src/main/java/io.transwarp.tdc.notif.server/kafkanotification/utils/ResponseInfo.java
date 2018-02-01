package io.transwarp.tdc.notif.server.kafkanotification.utils;

import java.util.Map;

public class ResponseInfo {
    private boolean isSuccess;
    private String message;
    private Map result;
    public ResponseInfo(String message){
        this.isSuccess = true;
        this.message = message;
    }

    public ResponseInfo(){
        this.isSuccess = true;
        this.message = null;
    }

    public ResponseInfo(boolean isSuccess,String message){
        this.isSuccess = true;
        this.message = message;
    }

    public Map getResult() {
        return result;
    }

    public void setResult(Map result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
