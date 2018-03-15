package io.transwarp.tdc.gn.client.rest;

import io.transwarp.tdc.gn.common.transport.*;

import java.util.List;

/**
 * 18-2-8 created by zado
 */
public interface GNRestClient {

//    TMetaInfo getMetaInfo();

    TConsumerOffset fetchOffset(String topic, String subscriber);

    TResult commitOffset(String topic, String subscriber, TOffsetCommit offsetCommit);

    TRecords consume(String topic, String subscriber, Integer count, String user);

    TResult produce(String topic, TPayload payload, Boolean ensureSuccess);

    TResult subscribe(String topic, String subscriber, String user);

    TResult unsubscribe(String topic, String subscriber);

    List<TSubscription> listSubscriptions(String subscriber);

    Void heartbeat(String topic, String subscriber, String user);

    List<TTopic> listTopics();

    TTopic getTopic(String name);

    /**
     * temporarily leave the topic, which is different from the unsubscribe()
     * method,
     * this method will only impact if the client is the master to consume a topic
     * @param topic
     * @param subscriber
     * @param user
     * @return
     */
    TResult leave(String topic, String subscriber, String user);
}
