package io.transwarp.tdc.gn.common.utils;

import io.transwarp.mangix.utils.sequence.Sequence;

/**
 * 18-2-5 created by zado
 */
public class GeneratorX {

    private static Sequence UID_GENERATOR = new Sequence(1, 1);

    /**
     * 生成long类型的id, id满足
     * 1.唯一
     * 2.自增
     */
    public static long generateUid() {
        return UID_GENERATOR.nextId();
    }
}
