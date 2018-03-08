package io.transwarp.tdc.gn.schema.concrete;

import io.transwarp.tdc.gn.schema.GNLevel;
import io.transwarp.tdc.gn.schema.GNSchema;
import io.transwarp.tdc.gn.schema.GNType;

/**
 *
 * 新产品、功能发布的广告通知
 *
 * 18-2-23 created by zado
 */
public class Release extends GNSchema {

    public Release() {
        super(GNType.ADVERTISEMENT, GNLevel.LOW);
    }
}
