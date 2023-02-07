package com.ujs.shop.common.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author mundo.wang
 * @date 2023/2/7 15:36
 */


@Data
public class EmployeeLoginRO {

    @NotBlank(message = "用户名不得为空")
    private String userName;

    @NotBlank(message = "密码不得为空")
    private String password;
}
