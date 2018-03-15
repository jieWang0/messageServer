package io.ts.tdc.gn.client.produce;

/**
 * 18-2-23 created by zado
 */
public interface ProducerFactory<T> {

    Producer<T> getInstance();
}
