package io.ts.tdc.gn.schema.concrete;

import io.ts.tdc.gn.schema.GNLevel;
import io.ts.tdc.gn.schema.GNSchema;
import io.ts.tdc.gn.schema.GNType;

/**
 * 实例的增、删、改的通知
 *
 * 实例的description需要用户定义
 * 比如删除(删除实例xxx成功)
 *
 * 18-2-23 created by zado
 */
public class InstanceOperation extends GNSchema {

    public InstanceOperation() {
        super(GNType.PROJECT, GNLevel.MEDIUM);
    }
}
