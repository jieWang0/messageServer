package io.transwarp.tdc.notif.client.recipes;

public interface ConsumerAdapter<T> {

    void addConsumeCallback(ConsumeCallback<T> onConsuming);

}
