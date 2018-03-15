package io.ts.tdc.gn.schema.concrete;

import io.ts.tdc.gn.schema.GNLevel;
import io.ts.tdc.gn.schema.GNSchema;
import io.ts.tdc.gn.schema.GNType;

/**
 * 资源不足
 *
 * 18-2-23 created by zado
 */
public class ResourceShortage extends GNSchema {

    private static final String DESC = "Resource Shortage";

    public ResourceShortage() {
        super(GNType.SECURITY, DESC, GNLevel.MEDIUM);
    }
}
