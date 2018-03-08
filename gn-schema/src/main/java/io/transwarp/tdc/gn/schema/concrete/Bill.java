package io.transwarp.tdc.gn.schema.concrete;

import io.transwarp.tdc.gn.schema.GNLevel;
import io.transwarp.tdc.gn.schema.GNSchema;
import io.transwarp.tdc.gn.schema.GNType;

/**
 * 账单通知
 *
 * 18-2-23 created by zado
 */
public class Bill extends GNSchema {

    private static final String DESC = "Bill Notice";

    public Bill() {
        super(GNType.FINANCE, DESC, GNLevel.MEDIUM);
    }
}
