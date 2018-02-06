package io.transwarp.tdc.gn.common;

public interface PayloadSerializer<T> {
    String serialize(T payload);
}
