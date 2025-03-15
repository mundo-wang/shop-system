package com.ujs.shop.common.global;

import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ControllerException;
import com.ujs.shop.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * @author mundo.wang
 * @date 2023/2/8 11:03
 * <p>
 * <p>
 * 对于一些全局异常的捕捉与集中处理
 */


@ResponseBody
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 对于MySQL数据库存在的问题统一返回给前端
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = SQLException.class)
    public ResponseBean<?> sqlException(SQLException exception) {
        log.error(exception.getMessage());
        return ResponseBean.fail(ResponseCodeEnum.MYSQL_ERROR);
    }


    /**
     * 捕捉一切在service抛出的异常，返回标准格式给前端
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ServiceException.class)
    public ResponseBean<?> serviceException(ServiceException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseBean.fail(exception.getErrorCodeEnum());
    }


    /**
     * 捕捉一切在controller抛出的异常，返回标准格式给前端
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ControllerException.class)
    public ResponseBean<?> controllerException(ControllerException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseBean.fail(exception.getErrorCodeEnum());
    }


    /**
     * 捕捉其余异常，返回标准格式给前端
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseBean<?> otherException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseBean.fail(ResponseCodeEnum.OTHER_ERROR);
    }


}
