package io.ts.tdc.gn.common.seder;

public interface PayloadSerializer<T> {
    String serialize(T payload);
}
