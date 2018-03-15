package io.ts.tdc.gn.schema;

/**
 * 消息通知的类型
 *
 * 18-2-23 created by zado
 */
public enum GNType {
    /**
     * 系统类通知
     */
    SYSTEM,

    /**
     * 财务类通知
     */
    FINANCE,

    /**
     * 安全类通知
     */
    SECURITY,

    /**
     * 项目相关通知
     */
    PROJECT,

    /**
     * 用户等管理相关通知
     */
    MANAGE,

    /**
     * 广告
     */
    ADVERTISEMENT,
}
