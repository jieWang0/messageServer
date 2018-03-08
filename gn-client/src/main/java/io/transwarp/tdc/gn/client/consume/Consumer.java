package io.transwarp.tdc.gn.client.consume;

import io.transwarp.tdc.gn.common.NotificationConsumerRecord;
import io.transwarp.tdc.gn.common.PartitionOffset;

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

    NotificationConsumerRecord<T> pollOnce(long timeoutMillis);

    /**
     * fetch data within timeout
     * @param timeoutMillis the timeout
     * @return the iterable records to consume
     */
    List<NotificationConsumerRecord<T>> poll(long timeoutMillis);

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
