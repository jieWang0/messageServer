package io.transwarp.tdc.gn.client.meta;

import io.transwarp.tdc.gn.common.MetaInfo;

/**
 * get meta info about client
 */
public interface MetaInfoRetriever extends AutoCloseable {

    MetaInfo metaInfo();

    String metaInfoSource();
}
