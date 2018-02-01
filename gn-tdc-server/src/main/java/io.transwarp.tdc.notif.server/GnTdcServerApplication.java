package io.transwarp.tdc.notif.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("io.transwarp.tdc.notif.server.kafkanotification.mapper")
public class GnTdcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GnTdcServerApplication.class, args);
    }

}