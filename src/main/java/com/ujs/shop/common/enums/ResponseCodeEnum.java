package com.ujs.shop.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author mundo.wang
 * @date 2023/2/6 14:47
 */


@AllArgsConstructor
@Getter
public enum ResponseCodeEnum {

    SUCCESS(100, "成功"),
    FAILED(101, "失败");

    private final Integer code;

    private final String message;


}
