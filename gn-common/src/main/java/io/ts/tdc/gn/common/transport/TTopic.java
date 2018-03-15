package io.ts.tdc.gn.common.transport;

/**
 * 18-2-6 created by zado
 */
public class TTopic {

    /**
     * 消息主题的名称
     * 唯一
     */
    private String name;

    /**
     * 消息主题的创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private long createTime;

    public TTopic() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
