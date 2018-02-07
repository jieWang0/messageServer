package io.transwarp.tdc.gn.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"io.transwarp.tdc.gn"})
@MapperScan("io.transwarp.tdc.gn.mapper")
@EnableScheduling
public class GnTdcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GnTdcServerApplication.class, args);
    }

}