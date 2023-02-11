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
    FAILED(200, "失败"),
    USER_NAME_ERROR(201, "员工用户名不得重复"),
    NO_SUCH_USER(202, "此用户不存在"),
    WRONG_PASSWORD(203, "原密码不正确"),
    SAME_PASSWORD(204, "新密码与原密码相同"),
    MYSQL_ERROR(205, "数据库错误"),
    PASSWORD_WRONG(206, "密码错误"),
    USER_DISABLE(207, "用户已被禁用"),
    JWT_TOKEN_ERROR(208, "校验失败，jwtToken可能已被篡改"),
    NO_PERMISSION(209, "无权限操作"),
    DISABLE_ERROR(210, "不能操作管理员状态"),
    OTHER_ERROR(211, "其他问题，请检查后端控制台"),
    OVERDUE(212, "未登录或登录已过期，请重新登录"),
    NO_LOGIN_MSG(213, "没有登录信息，请检查请求头");

    private final Integer code;

    private final String message;




}
