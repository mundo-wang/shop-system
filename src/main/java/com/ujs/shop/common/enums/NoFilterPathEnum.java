package com.ujs.shop.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mundo.wang
 * @date 2023/2/11 1:41
 */


@AllArgsConstructor
@Getter
public enum NoFilterPathEnum {

    PATH1("/shop/employee/login"),
//    PATH2("/shop/employee/logout"),
    PATH3("/backend/**"),
    PATH4("/front/**");

    private final String path;
}
