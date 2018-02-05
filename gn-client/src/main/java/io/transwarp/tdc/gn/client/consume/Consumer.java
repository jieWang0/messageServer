package io.transwarp.tdc.gn.client.consume;

import io.transwarp.tdc.gn.common.NotificationConsumerRecords;
import io.transwarp.tdc.gn.common.PartitionOffset;

import java.util.Collection;

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
     */
    void close();

}
