package io.ts.tdc.gn.client.recipes;

import io.ts.tdc.gn.client.ConsumeDedupeStrategy;
import io.ts.tdc.gn.client.ConsumePersistStrategy;
import io.ts.tdc.gn.client.ConsumeQueryStrategy;
import io.ts.tdc.gn.common.NotificationConsumerRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * in-memory hash map implementation, not thread safe as the consumer will be
 * only run on single thread
 * @param <T>
 */
public class InMemoryGuidStrategies<T> {
    private Map<String, NotificationConsumerRecord<T>> records = new HashMap<>();

    public ConsumePersistStrategy<T> persistStrategy() {
        return new ConsumePersistStrategy<T>() {
            @Override
            public void persist(NotificationConsumerRecord<T> record) {
                InMemoryGuidStrategies.this.persist(record);
            }

            @Override
            public void close() {
                InMemoryGuidStrategies.this.close();
            }
        };
    }

    public ConsumeDedupeStrategy<T> dedupeStrategy() {
        return this::isDuplicated;
    }

    public ConsumeQueryStrategy<T> queryStrategy() {
        return this::query;
    }

    private boolean isDuplicated(NotificationConsumerRecord<T> record) {
        if (record.guid() == null) {
            // guid not implemented, then dedupe can not be performed
            throw new IllegalArgumentException("Record.guid() is null");
        }
        return records.containsKey(record.guid());
    }

    private void persist(NotificationConsumerRecord<T> record) {
        if (record.guid() == null) {
            throw new IllegalArgumentException("Record.guid() is null");
        }
        records.put(record.guid(), record);
    }

    private NotificationConsumerRecord<T> query(String guid) {
        return records.get(guid);
    }

    private void close() {
        records.clear();
    }
}
