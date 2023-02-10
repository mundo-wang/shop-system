package com.ujs.shop.common.global;

import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.GlobalSQLExecption;
import com.ujs.shop.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * @author mundo.wang
 * @date 2023/2/8 11:03
 *
 *
 * 对于一些全局异常的捕捉与集中处理
 */


@ResponseBody
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(SQLException.class)
    public ResponseBean<String> sqlExceptionHandler(SQLException exception) {
        log.error(exception.getMessage());
        throw new GlobalSQLExecption(ResponseCodeEnum.MYSQL_ERROR);
    }



    @ExceptionHandler(value = ServiceException.class)
    public ResponseBean<?> catchException(ServiceException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseBean.fail(exception.getErrorCodeEnum());
    }


}
