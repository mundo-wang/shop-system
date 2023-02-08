package com.ujs.shop.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mundo.wang
 * @date 2023/2/8 14:05
 *
 * 配置mybatisplus的分页插件
 */


@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor interceptorConfig() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

}
