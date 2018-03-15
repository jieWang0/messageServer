package io.ts.tdc.gn.schema.concrete;

import io.ts.tdc.gn.schema.GNLevel;
import io.ts.tdc.gn.schema.GNSchema;
import io.ts.tdc.gn.schema.GNType;

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
