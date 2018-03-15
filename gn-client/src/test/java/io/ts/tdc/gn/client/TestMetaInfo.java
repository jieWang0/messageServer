package io.ts.tdc.gn.client;

import io.ts.tdc.gn.client.rest.GNRestClient;
import io.ts.tdc.gn.client.rest.GNRestClientFactory;
import io.ts.tdc.gn.client.rest.GNRestConfig;
import io.ts.tdc.gn.common.transport.TMetaInfo;
import org.junit.Before;
import org.junit.Test;

/**
 * 18-2-11 created by zado
 */
public class TestMetaInfo {

    private GNRestConfig config;
    private GNRestClient client;

    @Before
    public void setUp() throws Exception {
        config = new GNRestConfig();
        config.setLocation("http://localhost:23333");

        client = GNRestClientFactory.create(config);
    }

//    @Test
//    public void testMetaInfo() {
//        TMetaInfo metaInfo = client.getMetaInfo();
//        System.out.println(metaInfo);
//    }
}
