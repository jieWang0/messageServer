package io.transwarp.tdc.gn.client;

import io.transwarp.tdc.gn.client.consume.ConsumerArgs;
import io.transwarp.tdc.gn.client.meta.MetaInfoRetriever;
import io.transwarp.tdc.gn.common.seder.SerdeFactory;
import io.transwarp.tdc.tracing.retrofit.RetrofitArgs;

import java.util.Map;

/**
 * A generic consumer continuing consume notifications
 */
public interface GNConsumer<T>
        extends ShutdownStrategyConfigurable, HeartbeatDaemonConfigurable {

    /**
     * start the consumption
     *
     */
    void start();

    /**
     * stop the consumption
     */
    void shutdown();

    /**
     * shutdown with timeout
     * @param timeoutMillis
     */
    void shutdownAwait(long timeoutMillis) throws InterruptedException;

    /**
     * get the record type
     * @return
     */
    Class<T> getType();

    /**
     * builder provides more functions
     * like persist, de-dupe, etc.
     */
    interface Builder<T> {

        /**
         * set the handler to consume record
         * @param consumeHandler
         * @return
         */
        Builder consumeHandler(ConsumeHandler<T> consumeHandler);

        /**
         * Factory to generate serializer and deserializer
         * @param serdeFactory
         * @return
         */
        Builder serdeFactory(SerdeFactory<T> serdeFactory);

        /**
         * set consumer arguments
         * @param consumerArgs
         * @return
         */
        Builder consumerArgs(ConsumerArgs consumerArgs);

        /**
         * controls how to persist record
         * @param persistStrategy
         * @return
         */
        Builder persistStrategy(ConsumePersistStrategy<T> persistStrategy);

        /**
         * controls how to dedupe record
         * @return
         */
        Builder dedupeStrategy(ConsumeDedupeStrategy<T> dedupeStrategy);

        /**
         * controls how to shutdown the consuming process
         * @param shutdownStrategy
         * @return
         */
        Builder shutdownStrategy(ConsumeShutdownStrategy shutdownStrategy);

        /**
         * controls how to keep heartbeat with connection to server
         * @param heartbeatDaemon
         * @return
         */
        Builder heartbeatDaemon(ConsumeHeartbeatDaemon heartbeatDaemon);

        /**
         * controls how to commit offset after records processed
         * @param commitPolicy
         * @return
         */
        Builder commitPolicy(ConsumeCommitPolicy commitPolicy);

        /**
         * meta info retriever, required
         * @param metaRetriever
         * @return
         */
        Builder metaRetriever(MetaInfoRetriever metaRetriever);

        /**
         * build the GNConsumer
         * @return
         */
        GNConsumer build();
    }
}
