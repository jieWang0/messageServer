package io.transwarp.tdc.gn.schema.concrete;

import io.transwarp.tdc.gn.schema.GNLevel;
import io.transwarp.tdc.gn.schema.GNSchema;
import io.transwarp.tdc.gn.schema.GNType;

/**
 * 付费通知
 *
 * 18-2-23 created by zado
 */
public class Payment extends GNSchema {

    private static final String DESC = "Payment Notice";

    public Payment() {
        super(GNType.FINANCE, DESC, GNLevel.HIGH);
    }
}
