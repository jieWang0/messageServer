package io.ts.tdc.gn.common.seder;

/**
 * Payload是String格式的反系列化结果为原来的String
 *
 * 18-2-9 created by zado
 */
public class StringPayloadDeserializer implements PayloadDeserializer<String> {

    @Override
    public String deserialize(String str) {
        return str;
    }
}
