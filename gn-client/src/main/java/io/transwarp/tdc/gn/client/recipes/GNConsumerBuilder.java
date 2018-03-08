package io.transwarp.tdc.gn.client.recipes;

import io.transwarp.tdc.gn.client.ConsumeDedupeStrategy;
import io.transwarp.tdc.gn.client.ConsumeHandler;
import io.transwarp.tdc.gn.client.ConsumePersistStrategy;
import io.transwarp.tdc.gn.client.GNConsumer;
import io.transwarp.tdc.gn.common.seder.Converter;

import java.util.Map;

public class GNConsumerBuilder<T> implements GNConsumer.Builder<T> {
    private Class<T> recordType;
    private ConsumeHandler<T> consumeHandler;
    private Converter.Factory converterFactory;
    private Map<String, Object> options;
    private ConsumePersistStrategy<T> persistStrategy;
    private ConsumeDedupeStrategy<T> dedupeStrategy;

    public GNConsumerBuilder(Class<T> recordType) {
        this.recordType = recordType;
    }

    @Override
    public GNConsumer.Builder consumeHandler(ConsumeHandler<T> consumeHandler) {
        this.consumeHandler = consumeHandler;
        return this;
    }

    @Override
    public GNConsumer.Builder converterFactory(Converter.Factory converterFactory) {
        this.converterFactory = converterFactory;
        return this;
    }

    @Override
    public GNConsumer.Builder options(Map<String, Object> options) {
        this.options = options;
        return this;
    }

    @Override
    public GNConsumer.Builder persistStrategy(ConsumePersistStrategy<T> persistStrategy) {
        this.persistStrategy = persistStrategy;
        return this;
    }

    @Override
    public GNConsumer.Builder dedupeStrategy(ConsumeDedupeStrategy<T> dedupeStrategy) {
        this.dedupeStrategy = dedupeStrategy;
        return this;
    }

    @Override
    public GNConsumer build() {
        return new DefaultGNConsumer<>(recordType, consumeHandler, converterFactory,
                options, persistStrategy, dedupeStrategy);
    }

    public static <T> GNConsumer.Builder create(Class<T> recordType) {
        return new GNConsumerBuilder<>(recordType);
    }
}

