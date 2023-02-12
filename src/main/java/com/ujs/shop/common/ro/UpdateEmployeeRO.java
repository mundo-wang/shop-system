package com.ujs.shop.common.ro;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author mundo.wang
 * @date 2023/2/7 9:58
 */

@Data
public class UpdateEmployeeRO extends AddEmployeeRO{


    @NotBlank(message = "id不能为空")
    private String id;

}
