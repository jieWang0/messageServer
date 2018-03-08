package io.transwarp.tdc.gn.client.recipes;

import io.transwarp.tdc.gn.client.ConsumeDedupeStrategy;
import io.transwarp.tdc.gn.client.ConsumePersistStrategy;
import io.transwarp.tdc.gn.client.ConsumeQueryStrategy;
import io.transwarp.tdc.gn.common.NotificationConsumerRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * in-memory hash map implementation, not thread safe as the consumer will be
 * only run on single thread
 * @param <T>
 */
public class InMemoryGuidStrategy<T>
        implements ConsumeDedupeStrategy<T>,
        ConsumePersistStrategy<T>,
        ConsumeQueryStrategy<T> {
    private Map<String, NotificationConsumerRecord<T>> records;

    public InMemoryGuidStrategy() {
        this.records = new HashMap<>();
    }

    @Override
    public boolean isDuplicated(NotificationConsumerRecord<T> record) {
        if (record.guid() == null) {
            // guid not implemented, then dedupe can not be performed
            throw new IllegalArgumentException("Record.guid() is null");
        }
        return records.containsKey(record.guid());
    }

    @Override
    public void persist(NotificationConsumerRecord<T> record) {
        if (record.guid() == null) {
            throw new IllegalArgumentException("Record.guid() is null");
        }
        records.put(record.guid(), record);
    }

    @Override
    public NotificationConsumerRecord<T> query(String guid) {
        return records.get(guid);
    }

    @Override
    public void close() {
        records.clear();
    }
}
