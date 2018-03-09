package io.transwarp.tdc.gn.client.meta;

import io.transwarp.tdc.gn.common.MetaInfo;

import java.util.Objects;

/**
 * predefined meta info holder, can be used in unit tests
 */
public class PredefinedMetaInfoRetriever implements MetaInfoRetriever {
    private MetaInfo metaInfo;

    public PredefinedMetaInfoRetriever(MetaInfo metaInfo) {
        this.metaInfo = Objects.requireNonNull(metaInfo, "MetaInfo cannot be null");
    }

    @Override
    public MetaInfo metaInfo() {
        return metaInfo;
    }

    @Override
    public String metaInfoSource() {
        return metaInfo.getClass().getName();
    }

    @Override
    public void close() {

    }
}
