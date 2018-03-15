package io.ts.tdc.gn.client.rest;

import io.ts.tdc.gn.common.transport.*;
import io.ts.tdc.gn.common.transport.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * 18-2-8 created by zado
 */
public interface GNRestApi {

//    @GET(ApiConstants.METAINFO)
//    Call<TMetaInfo> getMetaInfo();

    @GET(ApiConstants.CONSUMER_OFFSET)
    Call<TConsumerOffset> fetchOffset(@Path("topic") String topic,
                                      @Path("subscriber") String subscriber);

    @POST(ApiConstants.CONSUMER_OFFSET_COMMIT)
    Call<TResult> commitOffset(@Path("topic") String topic,
                               @Path("subscriber") String subscriber,
                               @Body TOffsetCommit offsetCommit);

    @GET(ApiConstants.CONSUMER_RECORDS)
    Call<TRecords> consume(@Path("topic") String topic,
                           @Path("subscriber") String subscriber,
                           @Query("count") Integer count,
                           @Query("user") String user);

    @GET(ApiConstants.CONSUMER_HEARTBEAT)
    Call<Void> heartbeat(@Path("topic") String topic,
                         @Path("subscriber") String subscriber,
                         @Query("user") String user);

    @POST(ApiConstants.CONSUMER_SUBSCRIBE)
    Call<TResult> subscribe(@Path("topic") String topic,
                            @Path("subscriber") String subscriber,
                            @Query("user") String user);

    @DELETE(ApiConstants.CONSUMER_UNSUBSCRIBE)
    Call<TResult> unsubscribe(@Path("topic") String topic,
                              @Path("subscriber") String subscriber);

    @DELETE(ApiConstants.CONSUMER_LEAVE)
    Call<TResult> leave(@Path("topic") String topic,
                        @Path("subscriber") String subscriber,
                        @Path("user") String user);


    @GET(ApiConstants.CONSUMER_SUBSCRIPTIONS)
    Call<List<TSubscription>> listSubscriptions(@Path("subscriber") String subscriber);

    @POST(ApiConstants.PRODUCER_SEND)
    Call<TResult> produce(@Path("topic") String topic,
                          @Body TPayload payload,
                          @Query("ensureSuccess") Boolean ensureSuccess);

    @GET(ApiConstants.TOPIC_LIST)
    Call<List<TTopic>> listTopics();

    @GET(ApiConstants.TOPIC_GET)
    Call<TTopic> getTopic(@Path("name") String name);


}
