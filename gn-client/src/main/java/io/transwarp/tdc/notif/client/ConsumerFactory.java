package io.transwarp.tdc.notif.client;

public interface ConsumerFactory<T> {

    Consumer<T> getInstance(ConsumerArgs args);
}
