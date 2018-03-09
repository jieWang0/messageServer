package io.transwarp.tdc.gn.client.kafka;

import io.transwarp.tdc.gn.client.config.AbstractConsumerConfig;

public class KafkaConsumerConfig extends AbstractConsumerConfig{

    public static final String SERVER_LOCATION = PREFIX + "server.location";

    //是否启动offset的自动commit
    public static final String AUTO_COMMIT_ENABLE = PREFIX + "auto.commit.enable";

    //每次poll获取的记录数量
    public static final String MAX_POLL_RECORDS = PREFIX + "max.poll.records";

    //consumer key的序列化器，暂时用不到key，默认为String
    public static final String KEY_PAYLOAD_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";

    public static final String VALUE_PAYLOAD_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
}
