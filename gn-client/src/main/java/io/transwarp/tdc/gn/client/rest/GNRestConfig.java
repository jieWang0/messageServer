package io.transwarp.tdc.gn.client.rest;

import io.transwarp.mangix.web.client.Args;
import io.transwarp.mangix.web.client.ArgsBuilder;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 18-2-8 created by zado
 */
public class GNRestConfig {

    private String location;
    private HttpLoggingInterceptor.Level loggingLevel = HttpLoggingInterceptor.Level.BODY;
    private boolean timeoutEnabled = true;
    private int timeoutInMillis = 10000;
    private boolean circuitBreakerEnabled = true;
    private int circuitBreakerRollingWindowMillis = 30000;
    private int circuitBreakerSleepWindowMillis = 30000;
    private int circuitBreakerErrorPercentage = 50;
    private int circuitBreakerErrorVolume = 20;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HttpLoggingInterceptor.Level getLoggingLevel() {
        return loggingLevel;
    }

    public void setLoggingLevel(HttpLoggingInterceptor.Level loggingLevel) {
        this.loggingLevel = loggingLevel;
    }

    public boolean isTimeoutEnabled() {
        return timeoutEnabled;
    }

    public void setTimeoutEnabled(boolean timeoutEnabled) {
        this.timeoutEnabled = timeoutEnabled;
    }

    public int getTimeoutInMillis() {
        return timeoutInMillis;
    }

    public void setTimeoutInMillis(int timeoutInMillis) {
        this.timeoutInMillis = timeoutInMillis;
    }

    public boolean isCircuitBreakerEnabled() {
        return circuitBreakerEnabled;
    }

    public void setCircuitBreakerEnabled(boolean circuitBreakerEnabled) {
        this.circuitBreakerEnabled = circuitBreakerEnabled;
    }

    public int getCircuitBreakerRollingWindowMillis() {
        return circuitBreakerRollingWindowMillis;
    }

    public void setCircuitBreakerRollingWindowMillis(int circuitBreakerRollingWindowMillis) {
        this.circuitBreakerRollingWindowMillis = circuitBreakerRollingWindowMillis;
    }

    public int getCircuitBreakerSleepWindowMillis() {
        return circuitBreakerSleepWindowMillis;
    }

    public void setCircuitBreakerSleepWindowMillis(int circuitBreakerSleepWindowMillis) {
        this.circuitBreakerSleepWindowMillis = circuitBreakerSleepWindowMillis;
    }

    public int getCircuitBreakerErrorPercentage() {
        return circuitBreakerErrorPercentage;
    }

    public void setCircuitBreakerErrorPercentage(int circuitBreakerErrorPercentage) {
        this.circuitBreakerErrorPercentage = circuitBreakerErrorPercentage;
    }

    public int getCircuitBreakerErrorVolume() {
        return circuitBreakerErrorVolume;
    }

    public void setCircuitBreakerErrorVolume(int circuitBreakerErrorVolume) {
        this.circuitBreakerErrorVolume = circuitBreakerErrorVolume;
    }

    public Args asArgs() {
        return new ArgsBuilder()
            .setLocation(location.contains("://") ? location : "http://" + location)
            .setLoggingLevel(loggingLevel)
            .setTimeoutEnabled(timeoutEnabled)
            .setTimeoutInMillis(timeoutInMillis)
            .setCircuitBreakerEnabled(circuitBreakerEnabled)
            .setCircuitBreakerErrorPercentage(circuitBreakerErrorPercentage)
            .setCircuitBreakerErrorVolume(circuitBreakerErrorVolume)
            .setCircuitBreakerRollingWindowMillis(circuitBreakerRollingWindowMillis)
            .setCircuitBreakerSleepWindowMillis(circuitBreakerSleepWindowMillis)
            .build();
    }
}
