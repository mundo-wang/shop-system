package com.ujs.shop.common.ro;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author mundo.wang
 * @date 2023/2/7 16:00
 */

@Data
public class BasePageRO {


    @NotNull(message = "页码不能为空")
    @Length(min = 1, message = "页码不能小于{min}")
    private Integer page;


    @NotNull(message = "每页条数不能为空")
    @Length(min = 1, message = "每页条数不能小于{min}")
    private Integer size;

}
