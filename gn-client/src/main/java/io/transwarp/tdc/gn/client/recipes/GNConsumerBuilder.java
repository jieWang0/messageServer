package io.transwarp.tdc.gn.client.recipes;

import io.transwarp.tdc.gn.client.*;
import io.transwarp.tdc.gn.client.consume.ConsumerArgs;
import io.transwarp.tdc.gn.client.meta.MetaInfoRetriever;
import io.transwarp.tdc.gn.common.seder.DefaultJacksonFactory;
import io.transwarp.tdc.gn.common.seder.SerdeFactory;
import io.transwarp.tdc.tracing.retrofit.RetrofitArgs;

import java.util.Map;

public class GNConsumerBuilder<T> implements GNConsumer.Builder<T> {
    private Class<T> recordType;
    private ConsumeHandler<T> consumeHandler;
    private ConsumerArgs consumerArgs;
    private SerdeFactory serdeFactory;
    private ConsumePersistStrategy<T> persistStrategy;
    private ConsumeDedupeStrategy<T> dedupeStrategy;
    private ConsumeShutdownStrategy shutdownStrategy = new DefaultShutdownStrategy();
    private ConsumeHeartbeatDaemon heartbeatDaemon;
    private ConsumeCommitPolicy commitPolicy = ConsumeCommitPolicy.Batch;
    private MetaInfoRetriever metaRetriever;

    public GNConsumerBuilder(Class<T> recordType) {
        this.recordType = recordType;
        this.serdeFactory = new DefaultJacksonFactory<>(recordType);
    }

    @Override
    public GNConsumer.Builder consumeHandler(ConsumeHandler<T> consumeHandler) {
        this.consumeHandler = consumeHandler;
        return this;
    }

    @Override
    public GNConsumer.Builder consumerArgs(ConsumerArgs consumerArgs) {
        this.consumerArgs = consumerArgs;
        return this;
    }

    @Override
    public GNConsumer.Builder serdeFactory(SerdeFactory serdeFactory) {
        this.serdeFactory = serdeFactory;
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
    public GNConsumer.Builder shutdownStrategy(ConsumeShutdownStrategy shutdownStrategy) {
        this.shutdownStrategy = shutdownStrategy;
        return this;
    }

    @Override
    public GNConsumer.Builder heartbeatDaemon(ConsumeHeartbeatDaemon heartbeatDaemon) {
        this.heartbeatDaemon = heartbeatDaemon;
        return this;
    }

    @Override
    public GNConsumer.Builder commitPolicy(ConsumeCommitPolicy commitPolicy) {
        this.commitPolicy = commitPolicy;
        return this;
    }

    @Override
    public GNConsumer.Builder metaRetriever(MetaInfoRetriever metaRetriever) {
        this.metaRetriever = metaRetriever;
        return this;
    }

    @Override
    public GNConsumer build() {
        return new DefaultGNConsumer<>(recordType, consumeHandler, consumerArgs, serdeFactory,
                persistStrategy, dedupeStrategy, shutdownStrategy,
                heartbeatDaemon, commitPolicy, metaRetriever);
    }

    public static <T> GNConsumer.Builder create(Class<T> recordType) {
        return new GNConsumerBuilder<>(recordType);
    }
}

