package io.ts.tdc.gn.client;

/**
 * heartbeat Daemon to notify server the consumer is alive,
 * kafka already has built-in heartbeat thread,
 * it is mainly for DB implementation
 *
 * Note: the implementation of start() method should be non-blocking,
 * usually to start another thread or via ExecutorService.
 */
public interface ConsumeHeartbeatDaemon {

    void setInterval(int intervalMillis);

    /**
     * start the heartbeat daemon
     * should not block current thread
     */
    void start();

    /**
     * stop the heartbeat daemon
     */
    void close();
}
