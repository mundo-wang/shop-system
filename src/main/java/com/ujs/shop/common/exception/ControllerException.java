package com.ujs.shop.common.exception;

import com.ujs.shop.common.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mundo.wang
 * @date 2023/2/7 13:11
 */


@Getter
@Setter
public class ControllerException extends BaseException {

    public ControllerException(ResponseCodeEnum errorCodeEnum) {
        super(errorCodeEnum);
    }

    public ControllerException(ResponseCodeEnum errorCodeEnum, String message) {
        super(errorCodeEnum, message);
    }

    public ControllerException(ResponseCodeEnum errorCodeEnum, String message, Throwable throwable) {
        super(errorCodeEnum, message, throwable);
    }
}
