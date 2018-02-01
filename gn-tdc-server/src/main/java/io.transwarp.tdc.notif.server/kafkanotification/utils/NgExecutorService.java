package io.transwarp.tdc.notif.server.kafkanotification.utils;
import io.transwarp.tdc.notif.server.kafkanotification.dao.impl.NotificationDao;
import io.transwarp.tdc.notif.server.kafkanotification.entity.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Configuration
@EnableConfigurationProperties(KafkaConfigBean.class)
public class NgExecutorService {
    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private ProduceCommand produceCommand;

    @Autowired
    private KafkaConfigUtils kafkaConfigUtils;

    public static ExecutorService ngExecutorService = Executors.newCachedThreadPool();


    /**
     * desc：将之前produce失败后存储到DB的数据重新produce
     * */
    @Scheduled(cron = "0/50 * *  * * ? ")
    public void produceFromDB() {
        Properties properties = kafkaConfigUtils.getKafkaConfig();
        List<NotificationEntity> messageList = notificationDao.getMessage();
        messageList.forEach(NotificationEntity->{
            produceCommand.setMessage(NotificationEntity.getMessage());
            produceCommand.setProperties(properties);
            produceCommand.setTopic(NotificationEntity.getTopic());
            NgExecutorService.ngExecutorService.execute(produceCommand);
            notificationDao.deleteMessage(NotificationEntity.getId());
        });
    }
}
