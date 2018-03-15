package io.ts.tdc.gn.schema.concrete;

import io.ts.tdc.gn.schema.GNLevel;
import io.ts.tdc.gn.schema.GNSchema;
import io.ts.tdc.gn.schema.GNType;

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
