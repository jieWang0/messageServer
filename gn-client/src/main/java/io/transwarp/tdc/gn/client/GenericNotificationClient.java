package io.transwarp.tdc.gn.client;

public interface GenericNotificationClient<T> {

    void subscribe(String topic, ConsumeHandler<T> handler);

    void close();
}
