package io.ts.tdc.gn.service.kafka;

/**
 * This strategy is required to implement
 * 'at least once' or 'exactly once' guarantee
 */
public abstract class BackendProduceErrorStrategy implements ProduceErrorStrategy {
}
