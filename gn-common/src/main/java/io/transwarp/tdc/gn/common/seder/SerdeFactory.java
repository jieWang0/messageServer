package io.transwarp.tdc.gn.common.seder;

/**
 * abstract the creation of serializer and deserializer
 */
public interface SerdeFactory<T> {

    PayloadDeserializer<T> payloadDeserializer();

    PayloadSerializer<T> payloadSerializer();

}
