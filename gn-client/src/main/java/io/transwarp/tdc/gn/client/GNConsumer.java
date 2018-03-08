package io.transwarp.tdc.gn.client;

import io.transwarp.tdc.gn.common.seder.Converter;

import java.util.Map;

/**
 * A generic consumer continuing consume notifications
 */
public interface GNConsumer<T> {

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
         * Factory to generate serailizer and deserializer
         * @param converterFactory
         * @return
         */
        Builder converterFactory(Converter.Factory converterFactory);

        /**
         * Options for specific implementations
         * @return
         */
        Builder options(Map<String, Object> options);

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
         * build the GNConsumer
         * @return
         */
        GNConsumer build();
    }
}
