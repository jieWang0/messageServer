package io.transwarp.tdc.gn.client.db;

import io.transwarp.tdc.gn.client.GNUtils;
import io.transwarp.tdc.gn.client.rest.GNRestClient;
import io.transwarp.tdc.gn.client.rest.GNRestClientFactory;
import io.transwarp.tdc.gn.client.rest.GNRestConfig;
import io.transwarp.tdc.gn.common.ConsumerOffset;
import io.transwarp.tdc.gn.common.transport.TConsumerOffset;
import io.transwarp.tdc.gn.common.transport.TTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 18-2-23 created by zado
 */
public class DBGNUtils implements GNUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBGNUtils.class);

    private GNRestClient client;

    public DBGNUtils(String serverLocation) {
        buildGenericClient(serverLocation);
    }

    @Override
    public ConsumerOffset fetchConsumerOffset(String group, String topic) {
        TConsumerOffset rawOffset = client.fetchOffset(topic, group);

        ConsumerOffset consumerOffset = new ConsumerOffset();
        consumerOffset.setGroup(rawOffset.getSubscriber());
        consumerOffset.setTopic(rawOffset.getTopic());
        consumerOffset.setCurrentOffset(rawOffset.getCurrentOffset());
        consumerOffset.setLastCommitTime(rawOffset.getCommitTime());

        return consumerOffset;
    }

    @Override
    public void subscribe(String group, String topic) {
        client.subscribe(group, topic);
        LOGGER.info("Group[{}] success to subscribe topic[{}]", group, topic);
    }

    @Override
    public void unsubscribe(String group, String topic) {
        client.unsubscribe(group, topic);
        LOGGER.info("Group[{}] success to unsubscribe topic[{}]", group, topic);
    }

    @Override
    public List<String> listTopics() {
        List<String> topics = new ArrayList<>();
        for (TTopic tTopic : client.listTopics()) {
            topics.add(tTopic.getName());
        }

        return topics;
    }

    private void buildGenericClient(String serverLocation) {
        if (serverLocation == null || serverLocation.isEmpty()) {
            throw new IllegalArgumentException("DBGNUtils server location can not be null");
        }

        GNRestConfig clientConfig = new GNRestConfig();
        clientConfig.setLocation(serverLocation);

        this.client = GNRestClientFactory.create(clientConfig);
    }
}
