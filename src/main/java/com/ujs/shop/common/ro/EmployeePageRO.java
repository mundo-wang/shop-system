package com.ujs.shop.common.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author mundo.wang
 * @date 2023/2/7 15:55
 */

@Data
public class EmployeePageRO extends BasePageRO {

    private String name;
}
