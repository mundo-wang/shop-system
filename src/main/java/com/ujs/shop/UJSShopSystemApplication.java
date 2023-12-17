package com.ujs.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author mundo.wang
 * @date 2023/2/6 14:42
 *
 *
 * 主启动类
 */

@SpringBootApplication
@Slf4j
@ServletComponentScan
public class UJSShopSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(UJSShopSystemApplication.class, args);
        log.info("项目启动成功");
    }
}
