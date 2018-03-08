package io.transwarp.tdc.gn.client.rest;

import io.transwarp.tdc.gn.common.transport.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * 18-2-8 created by zado
 */
public interface GNRestApi {

    @GET(ApiConstants.METAINFO)
    Call<TMetaInfo> getMetaInfo();

    @GET(ApiConstants.CONSUMER_OFFSET)
    Call<TConsumerOffset> fetchOffset(@Query("topic") String topic,
                                      @Query("subscriber") String subscriber);

    @POST(ApiConstants.CONSUMER_OFFSET_COMMIT)
    Call<TResult> commitOffset(@Body TOffsetCommit offsetCommit);

    @GET(ApiConstants.CONSUMER_RECORDS_ONCE)
    Call<TRecord> consumeOne(@Query("topic") String topic,
                             @Query("subscriber") String subscriber,
                             @Query("autoCommit") Boolean autoCommit);

    @GET(ApiConstants.CONSUMER_RECORDS_BATCH)
    Call<List<TRecord>> consumeBatch(@Query("topic") String topic,
                                     @Query("subscriber") String subscriber,
                                     @Query("count") Integer count,
                                     @Query("autoCommit") Boolean autoCommit);

    @POST(ApiConstants.PRODUCER_SEND)
    Call<TResult> produce(@Path("topic") String topic,
                          @Body TPayload payload,
                          @Query("ensureSuccess") Boolean ensureSuccess);

    @POST(ApiConstants.SUBSCRIPTIONS_SUBSCRIBE)
    Call<TResult> subscribe(@Query("subscriber") String subscriber,
                            @Query("topic") String topic);

    @POST(ApiConstants.SUBSCRIPTIONS_UNSUBSCRIBE)
    Call<TResult> unsubscribe(@Query("subscriber") String subscriber,
                              @Query("topic") String topic);

    @GET(ApiConstants.SUBSCRIPTIONS_LIST)
    Call<List<TSubscription>> listSubscriptions(@Query("subscriber") String subscriber);

    @GET(ApiConstants.TOPIC_LIST)
    Call<List<TTopic>> listTopics();

    @GET(ApiConstants.TOPIC_GET)
    Call<TTopic> getTopic(@Path("name") String name);
}
