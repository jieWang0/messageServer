package io.ts.tdc.gn.client.db;

import io.ts.tdc.gn.client.config.AbstractConsumerConfig;
import io.ts.tdc.gn.client.config.AbstractConsumerConfig;

/**
 * 18-2-10 created by zado
 */
public class DBConsumerConfig extends AbstractConsumerConfig {

    // consumer接收消息服务器地址. rest api的地址
    public static final String SERVER_LOCATION = PREFIX + "server.location";

    // 是否开启consumer接收消息的超时
    public static final String SERVER_TIMEOUT_ENABLE = PREFIX + "server.timeout.enable";

    // 超时时间
    public static final String SERVER_TIMEOUT_MILLIS = PREFIX + "server.timeout.millis";

    // consumer消费的主题
    public static final String CONSUMER_TOPIC = PREFIX + "topic";

    // consumer所在的group
    public static final String CONSUMER_GROUP = PREFIX + "group";

    public static final String CONSUMER_USER = PREFIX + "user";

    // 消费完之后是否自动提交
    public static final String AUTO_COMMIT_ENABLE = PREFIX + "auto-commit.enable";

    /**
     * application context for runtime
     */
    public static final String CONSUMER_APPLICATION_CONTEXT = PREFIX + "application.context";
}
