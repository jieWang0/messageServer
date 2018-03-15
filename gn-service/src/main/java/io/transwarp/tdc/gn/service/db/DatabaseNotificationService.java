package io.transwarp.tdc.gn.service.db;

import io.transwarp.tdc.gn.common.NotificationConsumerRecords;
import io.transwarp.tdc.gn.common.PartitionOffset;
import io.transwarp.tdc.gn.common.TopicPartition;
import io.transwarp.tdc.gn.service.NotificationService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DatabaseNotificationService extends NotificationService {

    /**
     * inject persist strategy
     */
    void setPersistStrategy();

    /**
     * get consumer groups
     * @return
     */
    List<String> consumerGroups();

    /**
     * get assignments by consumer group
     * @param consumerGroup
     * @return
     */
    Set<TopicPartition> assignments(String consumerGroup);

    /**
     * get scriscriptions by consumer group
     * @param consumerGroup
     * @return
     */
    Set<String> subscription(String consumerGroup);

    /**
     * subscribe a topic
     * @param consumerGroup
     * @param topic
     */
    void subscribe(String consumerGroup, String topic);

    /**
     * poll records from a topic, given the consumer group
     * @param consumerGroup
     * @param batchSize
     * @return
     */
    NotificationConsumerRecords poll(String consumerGroup, int batchSize);

    /**
     * commit the offset consumed
     * @param consumerGroup
     * @param offsets
     */
    void commit(String consumerGroup, Map<TopicPartition, PartitionOffset> offsets);
}
