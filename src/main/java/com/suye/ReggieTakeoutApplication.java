package com.suye;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@MapperScan("com.suye.mapper")
@EnableCaching
public class ReggieTakeoutApplication {

    public static void main(String[] args) {
//        返回ioc容器
        ConfigurableApplicationContext run = SpringApplication.run(ReggieTakeoutApplication.class, args);
        log.info("项目启动成功！");

//        查看容器内的组件
//        String[] names = run.getBeanDefinitionNames();
//        for (String name : names) {
//            System.out.println(name);
//        }

    }

}
