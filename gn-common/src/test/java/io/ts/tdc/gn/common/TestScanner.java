package io.ts.tdc.gn.common;

import io.ts.tdc.gn.common.utils.ClazzScanner;
import io.ts.tdc.gn.schema.GNSchema;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * 18-2-24 created by zado
 */
public class TestScanner {

    @Test
    public void testScanSchema() {
        Set<Class<? extends GNSchema>> schemaClasses =
            ClazzScanner.findClasses("io.ts.tdc.gn.schema.concrete", GNSchema.class);

        Assert.assertTrue(schemaClasses.size() > 0);
        for (Class<?> clazz : schemaClasses) {
            System.out.println(clazz.getSimpleName());
        }
    }
}
