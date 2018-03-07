package io.transwarp.tdc.gn.client.rest;

import io.transwarp.mangix.web.client.ClientFactory;

/**
 * 18-2-8 created by zado
 */
public class GNRestClientFactory {

    // 生成rest client
    public static GNRestClient create(GNRestConfig config) {

        return ClientFactory.getClient(GNRestApi.class,
                                       GNRestClient.class,
                                       config.asArgs());
    }
}
