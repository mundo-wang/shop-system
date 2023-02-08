package com.ujs.shop.common.exception;

import com.ujs.shop.common.enums.ResponseCodeEnum;

/**
 * @author mundo.wang
 * @date 2023/2/8 14:01
 */
public class GlobalSQLExecption extends BaseException {
    public GlobalSQLExecption(ResponseCodeEnum errorCodeEnum) {
        super(errorCodeEnum);
    }

    public GlobalSQLExecption(ResponseCodeEnum errorCodeEnum, String message) {
        super(errorCodeEnum, message);
    }

    public GlobalSQLExecption(ResponseCodeEnum errorCodeEnum, String message, Throwable throwable) {
        super(errorCodeEnum, message, throwable);
    }
}
