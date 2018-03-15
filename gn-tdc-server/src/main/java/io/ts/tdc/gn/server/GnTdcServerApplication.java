package io.ts.tdc.gn.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = { "io.ts.tdc.gn" })
@MapperScan(basePackages = { "io.ts.tdc.gn.mapper" })
@EnableScheduling
public class GnTdcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GnTdcServerApplication.class, args);
    }

}