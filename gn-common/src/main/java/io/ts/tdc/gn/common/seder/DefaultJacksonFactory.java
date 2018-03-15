package io.ts.tdc.gn.common.seder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;

import java.io.IOException;

public class DefaultJacksonFactory<T> implements SerdeFactory<T> {
    private Class<T> clazz;
    private ObjectMapper objectMapper;

    public DefaultJacksonFactory(Class<T> clazz) {
        this(clazz, new ObjectMapper());
    }

    public DefaultJacksonFactory(Class<T> clazz, ObjectMapper objectMapper) {
        this.clazz = clazz;
        this.objectMapper = objectMapper;
    }

    @Override
    public PayloadDeserializer<T> payloadDeserializer() {
        return s -> {
            try {
                return objectMapper.readValue(s, clazz);
            } catch (IOException ioe) {
                throw new GNException(ErrorCode.SERDE_PARSE_ERROR, ioe);
            }
        };
    }

    @Override
    public PayloadSerializer<T> payloadSerializer() {
        return p -> {
            try {
                return objectMapper.writeValueAsString(p);
            } catch (JsonProcessingException jpe) {
                throw new GNException(ErrorCode.SERDE_PARSE_ERROR, jpe);
            }
        };
    }
}
