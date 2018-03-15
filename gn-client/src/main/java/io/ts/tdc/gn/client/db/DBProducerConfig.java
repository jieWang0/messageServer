package io.ts.tdc.gn.client.db;

import io.ts.tdc.gn.client.config.AbstractProducerConfig;
import io.ts.tdc.gn.client.config.AbstractProducerConfig;

/**
 * 18-2-9 created by zado
 */
public class DBProducerConfig extends AbstractProducerConfig {

    // producer发送到的消息服务器地址. rest api的地址
    public static final String SERVER_LOCATION = PREFIX + "server.location";

    // 是否开启producer到server的请求超时
    public static final String SERVER_TIMEOUT_ENABLE = PREFIX + "server.timeout.enable";

    // 超时时间
    public static final String SERVER_TIMEOUT_MILLIS = PREFIX + "server.timeout.millis";
}
