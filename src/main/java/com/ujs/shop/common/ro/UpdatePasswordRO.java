package com.ujs.shop.common.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author mundo.wang
 * @date 2023/2/7 16:31
 */


@Data
public class UpdatePasswordRO {

    private String id;

    @NotBlank(message = "老密码不得为空")
    private String oldPassword;

    @NotBlank(message = "新密码不得为空")
    private String newPassword;
}
