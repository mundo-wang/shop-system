package com.ujs.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@Slf4j
public class UJSShopSystemApplication {

    public static void main(String[] args) {
//        返回ioc容器
        ConfigurableApplicationContext run = SpringApplication.run(UJSShopSystemApplication.class, args);
        log.info("项目启动成功！");

//        查看容器内的组件
//        String[] names = run.getBeanDefinitionNames();
//        for (String name : names) {
//            System.out.println(name);
//        }

    }

}
