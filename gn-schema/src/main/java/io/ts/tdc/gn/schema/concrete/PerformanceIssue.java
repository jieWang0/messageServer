package io.ts.tdc.gn.schema.concrete;

import io.ts.tdc.gn.schema.GNLevel;
import io.ts.tdc.gn.schema.GNSchema;
import io.ts.tdc.gn.schema.GNType;

/**
 * 性能问题
 *
 * 18-2-23 created by zado
 */
public class PerformanceIssue extends GNSchema {

    private static final String DESC = "Performance Issue";

    public PerformanceIssue() {
        super(GNType.SECURITY, DESC, GNLevel.MEDIUM);
    }
}
