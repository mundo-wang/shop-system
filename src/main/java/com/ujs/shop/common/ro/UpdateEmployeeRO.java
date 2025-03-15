package com.ujs.shop.common.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author mundo.wang
 * @date 2023/2/7 9:58
 */

@Data
public class UpdateEmployeeRO extends AddEmployeeRO {


    @NotBlank(message = "id不能为空")
    private String id;

}
