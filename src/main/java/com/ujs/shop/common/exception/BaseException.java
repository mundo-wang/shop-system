package com.ujs.shop.common.exception;

import com.ujs.shop.common.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mundo.wang
 * @date 2023/2/7 12:55
 *
 *
 * 基础异常类
 */

@Getter
@Setter
public class BaseException extends RuntimeException {


    private ResponseCodeEnum errorCodeEnum;
    private String errMessage;


    public BaseException(ResponseCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.errorCodeEnum = errorCodeEnum;
    }


    public BaseException(ResponseCodeEnum errorCodeEnum, String message) {
        super(message);
        this.errorCodeEnum = errorCodeEnum;
        this.errMessage = message;
    }


    public BaseException(ResponseCodeEnum errorCodeEnum, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCodeEnum = errorCodeEnum;
        this.errMessage = message;
    }

}
