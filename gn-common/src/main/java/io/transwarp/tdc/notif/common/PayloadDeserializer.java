package io.transwarp.tdc.notif.common;

public interface PayloadDeserializer<T> {
    T deserialize(String str);
}
