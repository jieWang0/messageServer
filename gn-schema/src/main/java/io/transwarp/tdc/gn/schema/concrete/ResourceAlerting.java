package io.transwarp.tdc.gn.schema.concrete;

import io.transwarp.tdc.gn.schema.GNLevel;
import io.transwarp.tdc.gn.schema.GNSchema;
import io.transwarp.tdc.gn.schema.GNType;

/**
 * 资源预警
 *
 * 18-2-23 created by zado
 */
public class ResourceAlerting extends GNSchema {

    private static final String DESC = "Resource Alerting";

    public ResourceAlerting() {
        super(GNType.SECURITY, DESC, GNLevel.MEDIUM);
    }
}
