package io.transwarp.tdc.gn.common.seder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.transwarp.tdc.gn.common.exception.ErrorCode;
import io.transwarp.tdc.gn.common.exception.GNException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * implementation of PayloadDeserializer
 */
public class JacksonPayloadDeserializer<T> implements PayloadDeserializer<T> {

    protected final Class<T> targetType;

    protected final ObjectMapper objectMapper;

    private volatile ObjectReader objectReader;

    protected JacksonPayloadDeserializer() {
        this(null);
    }

    @SuppressWarnings("unchecked")
    public JacksonPayloadDeserializer(Class<T> targetType) {
        if (targetType == null) {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                targetType = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
            }
        }
        if (targetType == null) {
            throw new IllegalArgumentException("JsonDeserializer target type can not be resolved");
        }
        this.targetType = targetType;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public T deserialize(String str) {
        if (objectReader == null) {
            objectReader = objectMapper.readerFor(targetType);
        }

        try {
            return objectReader.readValue(str);
        } catch (IOException e) {
            throw new GNException(ErrorCode.PAYLOAD_DESERIALIZE_ERROR, e);
        }
    }
}
