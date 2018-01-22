package io.transwarp.tdc.gn.client.consume;

import io.transwarp.tdc.gn.common.NotificationConsumerRecords;
import io.transwarp.tdc.gn.common.OffsetAndMetadata;
import io.transwarp.tdc.gn.common.TopicPartition;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

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

    /**
     * get the assignments
     * @return
     */
    Set<TopicPartition> assignments();

    /**
     * get the current subscribed topic list
     * @return topic list
     */
    Set<String> subscriptions();

    /**
     * subscribe given list of topics,
     * empty list means 'unsubscribe'
     * @param topics list of topics which should not be null
     */
    void subscribe(Collection<String> topics);

    /**
     * cancel all subscriptions
     */
    void unsubscribe();

    /**
     * fetch data within timeout
     * @param timeoutMillis the timeout
     * @return the iterable records to consume
     */
    NotificationConsumerRecords<T> poll(long timeoutMillis);

    /**
     * commit all of polled records as consumed.
     */
    void commit();

    /**
     * commit given offsets
     * @param offsets
     */
    void commit(Map<TopicPartition, OffsetAndMetadata> offsets);

//    void seek(TopicPartition partition, long offset);
//
//    void seekToBeginning(Collection<TopicPartition> partitions);
//
//    void seekToEnd(Collection<TopicPartition> partitions);

    /**
     * close the consumer
     */
    void close();

}
