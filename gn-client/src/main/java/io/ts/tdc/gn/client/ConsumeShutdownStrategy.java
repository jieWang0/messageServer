package io.ts.tdc.gn.client;

public interface ConsumeShutdownStrategy {

    /**
     * indicates whether the shutdown command is called
     * @return
     */
    boolean isShutdown();

    /**
     * shutdown the consuming process
     */
    void shutdown();
}
