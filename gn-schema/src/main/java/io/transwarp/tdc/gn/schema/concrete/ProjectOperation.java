package io.transwarp.tdc.gn.schema.concrete;

import io.transwarp.tdc.gn.schema.GNLevel;
import io.transwarp.tdc.gn.schema.GNSchema;
import io.transwarp.tdc.gn.schema.GNType;

/**
 * 项目的增、删、改的通知
 *
 * 项目通知的description需要用户自己定义
 * 比如是删除项目(删除项目xx成功)
 *
 * 18-2-23 created by zado
 */
public class ProjectOperation extends GNSchema {

    public ProjectOperation() {
        super(GNType.PROJECT, GNLevel.LOW);
    }
}
