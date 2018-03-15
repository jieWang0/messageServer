package io.ts.tdc.gn.schema.concrete;

import io.ts.tdc.gn.schema.GNLevel;
import io.ts.tdc.gn.schema.GNSchema;
import io.ts.tdc.gn.schema.GNType;

/**
 * 促销的广告通知
 *
 * 18-2-23 created by zado
 */
public class Promotion extends GNSchema {

    private static final String DESC = "Promotion";

    public Promotion() {
        super(GNType.ADVERTISEMENT, DESC, GNLevel.LOW);
    }
}
