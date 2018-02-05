package io.transwarp.tdc.gn.common;

public interface PayloadDeserializer<T> {
    T deserialize(String str);
}
