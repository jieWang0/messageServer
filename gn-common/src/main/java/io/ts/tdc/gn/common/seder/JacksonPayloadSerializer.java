package io.ts.tdc.gn.common.seder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;

public class JacksonPayloadSerializer<T> implements PayloadSerializer<T> {

    protected final ObjectMapper objectMapper;

    public JacksonPayloadSerializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String serialize(T payload) {
        String result = null;
        if (payload != null) {
            try {
                result = objectMapper.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                throw new GNException(ErrorCode.PAYLOAD_SERIALIZE_ERROR, e);
            }
        }

        return result;
    }
}
