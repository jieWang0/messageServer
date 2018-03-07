package io.transwarp.tdc.gn.schema.concrete;

import io.transwarp.tdc.gn.schema.GNLevel;
import io.transwarp.tdc.gn.schema.GNSchema;
import io.transwarp.tdc.gn.schema.GNType;

/**
 * 系统升级通知
 *
 * 18-2-23 created by zado
 */
public class SysUpgrade extends GNSchema {

    private static final String DESC = "System Upgrade";

    public SysUpgrade() {
        super(GNType.SYSTEM, DESC, GNLevel.HIGH);
    }
}
