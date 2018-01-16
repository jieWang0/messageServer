package io.transwarp.tdc.notif.common;

public interface PayloadSerializer<T> {
    String serialize(T payload);
}
