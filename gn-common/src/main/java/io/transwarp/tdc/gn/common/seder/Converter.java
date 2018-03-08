package io.transwarp.tdc.gn.common.seder;

public interface Converter<T, U> {

    U convert(T value);

    interface Factory {

        Converter<String, ?> payloadDeserializer();

        Converter<?, String> payloadSerializer();
    }
}
