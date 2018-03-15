package io.ts.tdc.gn.common.seder;

import io.ts.tdc.gn.common.utils.ClazzScanner;
import io.ts.tdc.gn.schema.GNSchema;
import io.ts.tdc.gn.common.utils.ClazzScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册和查找数据类型对应的deserializer
 *
 * 18-2-24 created by zado
 */
public class DeserializerRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeserializerRegistry.class);

    private static final String GN_SCHEMA_PACKAGE = "io.ts.tdc.gn.schema.concrete";
    private static Map<Class<?>, PayloadDeserializer> DESERIALIZER_CACHE;

    static {
        if (DESERIALIZER_CACHE == null) {
            DESERIALIZER_CACHE = new ConcurrentHashMap<>();
        }

        for (Class<?> clazz : ClazzScanner.findClasses(GN_SCHEMA_PACKAGE, GNSchema.class)) {
            register(clazz, new JacksonPayloadDeserializer<>(clazz));
        }
    }

    public static <T> void register(Class<? extends T> clazz, PayloadDeserializer<? extends T> deserializer) {
        if (clazz == null || deserializer == null) {
            throw new IllegalArgumentException("Clazz or deserializer cannot be null");
        }

        if (DESERIALIZER_CACHE.containsKey(clazz)) {
            LOGGER.warn("Class[{}] deserializer is already registered", clazz.toGenericString());
            return;
        }

        DESERIALIZER_CACHE.put(clazz, deserializer);
        LOGGER.info("Register class[{}] deserializer", clazz.toGenericString());
    }

    @SuppressWarnings("unchecked")
    public static <T> PayloadDeserializer<T> get(Class<T> clazz) {
        if (!DESERIALIZER_CACHE.containsKey(clazz)) {
            throw new IllegalArgumentException("Class[" + clazz.toGenericString() +"] serializer is not registered before");
        }

        return DESERIALIZER_CACHE.get(clazz);
    }
}
