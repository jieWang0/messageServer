package io.transwarp.tdc.gn.schema.concrete;

import io.transwarp.tdc.gn.schema.GNLevel;
import io.transwarp.tdc.gn.schema.GNSchema;
import io.transwarp.tdc.gn.schema.GNType;

/**
 * 实例模板的增、删、改的通知
 *
 * 18-2-23 created by zado
 */
public class TemplateOperation extends GNSchema {

    public TemplateOperation() {
        super(GNType.PROJECT, GNLevel.MEDIUM);
    }
}
