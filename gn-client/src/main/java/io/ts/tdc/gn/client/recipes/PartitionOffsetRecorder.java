package io.ts.tdc.gn.client.recipes;

import io.ts.tdc.gn.common.PartitionOffset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PartitionOffsetRecorder {
    private Map<Integer, PartitionOffset> offsets;

    public PartitionOffsetRecorder() {
        this.offsets = new HashMap<>();
    }

    public void inc(int partition, long offset) {
        PartitionOffset po = offsets.get(partition);
        if (po == null) {
            po = new PartitionOffset(partition, offset);
            offsets.put(partition, po);
        }
        if (po.getOffset() < offset) {
            po.setOffset(offset);
        }
    }

    public Collection<PartitionOffset> get() {
        return new ArrayList<>(offsets.values());
    }
}
