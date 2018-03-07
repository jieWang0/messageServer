package io.transwarp.tdc.gn.common.seder;

public interface PayloadDeserializer<T> {
    T deserialize(String str);
}
