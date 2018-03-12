package io.transwarp.tdc.gn.common.transport;

/**
 * 18-2-23 created by zado
 */
public class TOffsetCommit {

    private String user;

    private long offset;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
