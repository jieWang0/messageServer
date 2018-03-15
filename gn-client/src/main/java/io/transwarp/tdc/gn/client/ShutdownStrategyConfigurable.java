package io.transwarp.tdc.gn.client;

/**
 * implement this strategy to be able to reset a shutdown strategy
 */
public interface ShutdownStrategyConfigurable {

    void setShutdownStrategy(ConsumeShutdownStrategy shutdownStrategy);
}
