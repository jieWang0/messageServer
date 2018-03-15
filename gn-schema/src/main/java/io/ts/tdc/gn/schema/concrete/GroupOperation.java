package io.ts.tdc.gn.schema.concrete;

import io.ts.tdc.gn.schema.GNLevel;
import io.ts.tdc.gn.schema.GNSchema;
import io.ts.tdc.gn.schema.GNType;

/**
 * 组增、删、改的通知
 *
 * 18-2-23 created by zado
 */
public class GroupOperation extends GNSchema {

    public GroupOperation() {
        super(GNType.MANAGE, GNLevel.LOW);
    }
}
