package io.transwarp.tdc.gn.schema.concrete;

import io.transwarp.tdc.gn.schema.GNLevel;
import io.transwarp.tdc.gn.schema.GNSchema;
import io.transwarp.tdc.gn.schema.GNType;

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
