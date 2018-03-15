package io.ts.tdc.gn.client.consume;

import io.ts.tdc.gn.common.NotificationConsumerRecord;
import io.ts.tdc.gn.common.NotificationConsumerRecords;
import io.ts.tdc.gn.common.PartitionOffset;

import java.util.Collection;
import java.util.List;

/**
 * Notif message consumer
 *
 * Not thread safe
 *
 */
public interface Consumer<T> {

    /**
     * get the group name of consumer,
     * @return
     */
    String group();

    String topic();

//    NotificationConsumerRecord<T> pollOnce(long timeoutMillis);

    /**
     * fetch data within timeout
     * @param timeoutMillis the timeout
     * @return the iterable records to consume
     */
    NotificationConsumerRecords<T> poll(long timeoutMillis);

    /**
     * commit given offset
     * @param partitionOffsets
     */
    void commit(Collection<PartitionOffset> partitionOffsets);

    /**
     * close the consumer
     * may throw Shutdown
     */
    void close();

}
