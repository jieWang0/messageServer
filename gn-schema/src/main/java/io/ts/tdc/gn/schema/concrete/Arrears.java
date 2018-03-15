package io.ts.tdc.gn.schema.concrete;

import io.ts.tdc.gn.schema.GNLevel;
import io.ts.tdc.gn.schema.GNSchema;
import io.ts.tdc.gn.schema.GNType;

/**
 * 欠费通知
 *
 * 18-2-23 created by zado
 */
public class Arrears extends GNSchema {

    private static final String DESC = "Arrears Notice";

    public Arrears() {
        super(GNType.FINANCE, DESC, GNLevel.HIGH);
    }
}
