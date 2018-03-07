package io.transwarp.tdc.gn.common.seder;

import io.transwarp.tdc.gn.common.utils.ClazzScanner;
import io.transwarp.tdc.gn.schema.GNSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册和查找数据类型对应的serializer
 *
 * 18-2-24 created by zado
 */
public class SerializerRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerializerRegistry.class);

    private static final String GN_SCHEMA_PACKAGE = "io.transwarp.tdc.gn.schema.concrete";
    private static Map<Class<?>, PayloadSerializer> SERIALIZER_CACHE;

    static {
        if (SERIALIZER_CACHE == null) {
            SERIALIZER_CACHE = new ConcurrentHashMap<>();
        }

        // 注册已经定义好的数据类型的serializer
        for (Class<?> clazz : ClazzScanner.findClasses(GN_SCHEMA_PACKAGE, GNSchema.class)) {
            register(clazz, new JacksonPayloadSerializer<>());
        }
    }

    public static <T> void register(Class<? extends T> clazz, PayloadSerializer<? extends T> serializer) {
        if (clazz == null || serializer == null) {
            throw new IllegalArgumentException("Clazz or serializer cannot be null");
        }

        if (SERIALIZER_CACHE.containsKey(clazz)) {
            LOGGER.warn("Class[{}] serializer is already registered", clazz.toGenericString());
            return;
        }

        SERIALIZER_CACHE.put(clazz, serializer);
        LOGGER.info("Register class[{}] serializer", clazz.toGenericString());
    }

    @SuppressWarnings("unchecked")
    public static <T> PayloadSerializer<T> get(Class<T> clazz) {
        if (!SERIALIZER_CACHE.containsKey(clazz)) {
            throw new IllegalArgumentException("Class[" + clazz.toGenericString() +"] serializer is not registered before");
        }

        return SERIALIZER_CACHE.get(clazz);
    }
}
