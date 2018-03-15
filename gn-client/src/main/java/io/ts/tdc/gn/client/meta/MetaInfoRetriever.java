package io.ts.tdc.gn.client.meta;

import io.ts.tdc.gn.common.MetaInfo;

/**
 * get meta info about client
 */
public interface MetaInfoRetriever extends AutoCloseable {

    MetaInfo metaInfo();

    String metaInfoSource();
}
