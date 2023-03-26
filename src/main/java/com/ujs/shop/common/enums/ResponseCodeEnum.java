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
    NO_LOGIN_MSG(213, "没有登录信息，请检查请求头"),
    STATUS_ERROR(214, "请先禁用员工再删除"),
    NO_SUCH_CATEGORY(215, "没有此分类"),
    NO_SUCH_GOODS(216, "没有此商品"),
    GOODS_ON_SALE(217, "请先停售商品再删除"),
    GOODS_NAME_UNIQUE(218, "商品名重复"),
    PACKAGE_NAME_UNIQUE(219, "套餐名重复"),
    NO_SUCH_PACHAGE(220, "没有此套餐"),
    OUT_OF_ALLOWANCE(221, "套餐内商品数量超过库存"),
    PACKAGE_ON_SALE(222, "请先停售套餐再删除"),
    GOODS_DISABLE(223, "商品已被停售，无法加入套餐"),
    PHONE_UNIQUE(224, "电话号已被注册"),
    USERNAME_UNIQUE(225, "用户名重复"),
    VERIFY_ERROR(226, "验证码错误"),
    VERIFY_OVERDUE(227, "验证码已过期"),
    NO_SUCH_ADDRESS(228, "没有此地址"),
    VERIFY_ALREADY(229, "验证码已发送，两分钟内不得重复获取"),
    DEFAULT_ADDRESS(230, "默认地址不能删除"),
    ;

    private final Integer code;

    private final String message;


}
