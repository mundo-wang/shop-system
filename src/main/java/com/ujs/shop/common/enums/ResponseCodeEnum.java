package com.ujs.shop.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mundo.wang
 * @date 2023/2/6 14:47
 */


@AllArgsConstructor
@Getter
public enum ResponseCodeEnum {

    SUCCESS(100, "成功"),
    FAILED(101, "失败"),
    USER_NAME_ERROR(102, "员工用户名不得重复"),
    NO_SUCH_USER(103, "此用户不存在"),
    WRONG_PASSWORD(104, "原密码不正确"),
    SAME_PASSWORD(105, "新密码与原密码相同"),
    MYSQL_ERROR(106, "数据库错误"),
    PASSWORD_WRONG(107, "密码错误"),
    USER_DISABLE(108, "用户已被禁用"),
    JWT_TOKEN_ERROR(109, "校验失败，jwtToken可能已被篡改");


    private final Integer code;

    private final String message;




}
