package com.ujs.shop.common.global;

import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @author mundo.wang
 * @date 2023/2/6 20:37
 *
 * 常量表，所有用到的常量都从这取
 */
public class ConstantBean {

    /**
     * UUID主键统一从这里拿
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 初始密码设置为123456，经过MD5摘要转换为字符串
     */
    public static final String INITPASSWORD = DigestUtils.md5DigestAsHex("123456".getBytes());


    /**
     * 管理员用户名
     */
    public static final String SUPER_USER = "admin";


    /**
     * JWT使用到的密钥
     */
    public static final String SECRET = "ujs-shop-system-jwt-secret";


    /**
     * 从请求头中拿取对应属性值名称
     */
    public static final String AUTHORIZATION = "Authorization";


}
