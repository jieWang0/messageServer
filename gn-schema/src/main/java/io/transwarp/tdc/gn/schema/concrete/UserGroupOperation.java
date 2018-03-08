package io.transwarp.tdc.gn.schema.concrete;

import io.transwarp.tdc.gn.schema.GNLevel;
import io.transwarp.tdc.gn.schema.GNSchema;
import io.transwarp.tdc.gn.schema.GNType;

/**
 * 项目的用户、组的通知
 *
 * 具体的description需要用户定义
 * 比如添加(添加用户xxx到项目成功)
 *
 * 18-2-23 created by zado
 */
public class UserGroupOperation extends GNSchema {

    public UserGroupOperation() {
        super(GNType.PROJECT, GNLevel.LOW);
    }
}
