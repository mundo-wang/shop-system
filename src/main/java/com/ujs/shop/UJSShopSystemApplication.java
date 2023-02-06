package com.ujs.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@Slf4j
public class UJSShopSystemApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(UJSShopSystemApplication.class, args);
        log.info("项目启动成功！");
    }

}
