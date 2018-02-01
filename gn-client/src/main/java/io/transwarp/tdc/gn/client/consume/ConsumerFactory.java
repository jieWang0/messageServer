package io.transwarp.tdc.gn.client.consume;

public interface ConsumerFactory<T> {

    Consumer<T> getInstance(ConsumerArgs args);
}
