package io.transwarp.tdc.gn.client.rest;

/**
 * 18-2-8 created by zado
 */
final class ApiConstants {

    // 当前client使用的api版本
    public static final String API_VERSION = "api/v1";

    // metaInfo相关api
    public static final String METAINFO = API_VERSION + "/metaInfo";

    // consumer相关api
    public static final String CONSUMER = API_VERSION +  "/consumers";
    public static final String CONSUMER_OFFSET = CONSUMER + "/offsets";
    public static final String CONSUMER_OFFSET_COMMIT = CONSUMER + "/offsets/commit";
    public static final String CONSUMER_RECORDS_ONCE = CONSUMER + "/records/once";
    public static final String CONSUMER_RECORDS_BATCH = CONSUMER + "/records/batch";

    // producer相关api
    public static final String PRODUCER = API_VERSION + "/producers";
    public static final String PRODUCER_SEND = PRODUCER + "/{topic}";

    // subscription相关api
    public static final String SUBSCRIPTIONS = API_VERSION + "/subscriptions";
    public static final String SUBSCRIPTIONS_LIST = SUBSCRIPTIONS;
    public static final String SUBSCRIPTIONS_SUBSCRIBE = SUBSCRIPTIONS + "/subscribe";
    public static final String SUBSCRIPTIONS_UNSUBSCRIBE = SUBSCRIPTIONS + "/unsubscribe";

    // topic相关api
    public static final String TOPIC = API_VERSION + "/topics";
    public static final String TOPIC_LIST = TOPIC;
    public static final String TOPIC_GET = TOPIC + "/{name}";

    //kafka实现下的producer api
    public static final String KAFKA_PRODUCER_SEND = API_VERSION + "/{topic}";
}
