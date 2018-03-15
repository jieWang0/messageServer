package io.ts.tdc.gn.model;

import java.util.Collections;
import java.util.List;

/**
 * add additional information about whether the current user
 * is consuming the topic
 */
public class Records {
    // whether the consumer should consume the topic
    private boolean toConsume;
    // whether the records are locked and not acquired by other concurrent fetcher
    private boolean locked;
    private List<Record> data;

    public Records() {
    }

    public Records(boolean toConsume, boolean locked, List<Record> data) {
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

    public List<Record> getData() {
        return data;
    }

    public void setToConsume(boolean toConsume) {
        this.toConsume = toConsume;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setData(List<Record> data) {
        this.data = data;
    }

    public static Records empty(boolean toConsume, boolean locked) {
        return new Records(toConsume, locked, Collections.emptyList());
    }

    public static Records data(List<Record> data) {
        return new Records(true, false, data);
    }
}
