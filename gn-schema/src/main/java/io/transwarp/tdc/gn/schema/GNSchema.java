package io.transwarp.tdc.gn.schema;

import java.util.Date;

/**
 * 18-2-23 created by zado
 */
public class GNSchema {

    private Long id;

    private GNType type;

    private String description;

    private String content;

    private GNLevel level;

    private Date createTime;

    public GNSchema() {
    }

    protected GNSchema(GNType type, GNLevel level) {
        this.type = type;
        this.level = level;
    }

    protected GNSchema(GNType type, String description, GNLevel level) {
        this.type = type;
        this.description = description;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GNType getType() {
        return type;
    }

    public void setType(GNType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GNLevel getLevel() {
        return level;
    }

    public void setLevel(GNLevel level) {
        this.level = level;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "GNSchema{" +
            "id=" + id +
            ", type=" + type +
            ", description='" + description + '\'' +
            ", content='" + content + '\'' +
            ", level=" + level +
            ", createTime=" + createTime +
            '}';
    }
}
