package io.transwarp.tdc.gn.common.transport;

import java.util.List;

public class TRecords {
    private boolean toConsume;
    private boolean locked;
    private List<TRecord> data;

    public TRecords() {}

    public TRecords(boolean toConsume, boolean locked, List<TRecord> data) {
        this.toConsume = toConsume;
        this.locked = locked;
        this.data = data;
    }

    public boolean isToConsume() {
        return toConsume;
    }

    public boolean isLocked() {
        return locked;
    }

    public List<TRecord> getData() {
        return data;
    }

    public void setToConsume(boolean toConsume) {
        this.toConsume = toConsume;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setData(List<TRecord> data) {
        this.data = data;
    }
}
