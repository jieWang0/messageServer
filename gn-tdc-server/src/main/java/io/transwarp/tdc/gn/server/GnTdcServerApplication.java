package io.transwarp.tdc.gn.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"io.transwarp.tdc.gn"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class GnTdcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GnTdcServerApplication.class, args);
    }

}