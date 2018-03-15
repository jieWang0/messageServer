package io.ts.tdc.gn.dlock;

import io.ts.tdc.dlock.model.DLockType;

/**
 * 18-2-8 created by zado
 */
public enum GNLockType implements DLockType {
    CONSUME_RECORD(1, GNLockType.CONSUME_RECORD_VALUE),
    CONSUME_OFFSET(2, GNLockType.CONSUME_OFFSET_VALUE);

    private final int id;
    private final String name;

    GNLockType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    // 锁的名称常量
    public static final String CONSUME_RECORD_VALUE = "CONSUME_RECORD";
    public static final String CONSUME_OFFSET_VALUE = "CONSUME_OFFSET";
}
