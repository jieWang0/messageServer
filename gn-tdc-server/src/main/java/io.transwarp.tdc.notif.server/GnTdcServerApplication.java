package io.transwarp.tdc.notif.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GnTdcServerApplication {

    @RequestMapping("/")
    public String home() {
        return "docker部署测试";
    }

    public static void main(String[] args) {
        SpringApplication.run(GnTdcServerApplication.class, args);
    }

}