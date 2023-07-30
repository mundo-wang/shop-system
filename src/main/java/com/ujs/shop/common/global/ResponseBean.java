package com.ujs.shop.common.global;

import com.ujs.shop.common.enums.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mundo.wang
 * @date 2023/2/6 13:53
 *
 * 返回给前端的统一格式
 */


@Data
@AllArgsConstructor
public final class ResponseBean<T> implements Serializable {

    /**
     * 响应编码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 返回给前端的数据
     */
    private T data;

    /**
     * 成功
     */
    public static ResponseBean<?> success() {
        return new ResponseBean<>(ResponseCodeEnum.SUCCESS.getCode(),
                ResponseCodeEnum.SUCCESS.getMessage(), null);
    }

    public static <T> ResponseBean<T> success(T data) {
        return new ResponseBean<>(ResponseCodeEnum.SUCCESS.getCode(),
                ResponseCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 失败
     */
    public static ResponseBean<?> fail(ResponseCodeEnum code) {
        return new ResponseBean<>(code.getCode(), code.getMessage(), null);
    }

    public static ResponseBean<?> fail(ResponseCodeEnum code, String message) {
        return new ResponseBean<>(code.getCode(), message, null);
    }
}
