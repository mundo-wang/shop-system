package com.ujs.shop.common.exception;

import com.ujs.shop.common.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mundo.wang
 * @date 2023/2/7 13:10
 */

@Getter
@Setter
public class ServiceException extends BaseException {

    public ServiceException(ResponseCodeEnum errorCodeEnum) {
        super(errorCodeEnum);
    }

    public ServiceException(ResponseCodeEnum errorCodeEnum, String message) {
        super(errorCodeEnum, message);
    }

    public ServiceException(ResponseCodeEnum errorCodeEnum, String message, Throwable throwable) {
        super(errorCodeEnum, message, throwable);
    }
}
