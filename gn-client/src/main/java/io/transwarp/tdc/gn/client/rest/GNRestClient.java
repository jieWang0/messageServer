package io.transwarp.tdc.gn.client.rest;

import io.transwarp.tdc.gn.common.transport.*;

import java.util.List;

/**
 * 18-2-8 created by zado
 */
public interface GNRestClient {

    TMetaInfo getMetaInfo();

    TConsumerOffset fetchOffset(String topic, String subscriber);

    TResult commitOffset(TOffsetCommit offsetCommit);

    TRecord consumeOne(String topic, String subscriber, Boolean autoCommit);

    List<TRecord> consumeBatch(String topic, String subscriber, Integer count, Boolean autoCommit);

    TResult produce(String topic, TPayload payload, Boolean ensureSuccess);

    TResult subscribe(String subscriber, String topic);

    TResult unsubscribe(String subscriber, String topic);

    List<TSubscription> listSubscriptions(String subscriber);

    List<TTopic> listTopics();

    TTopic getTopic(String name);

    TResult kafkaProduce(String topic, TPayload payload, Boolean ensureSuccess);
}
