package io.ts.tdc.gn.common.seder;

/**
 * Payload是String格式的序列化结果为原来的String
 *
 * 18-2-9 created by zado
 */
public class StringPayloadSerializer implements PayloadSerializer<String> {

    @Override
    public String serialize(String payload) {
        return payload;
    }
}
