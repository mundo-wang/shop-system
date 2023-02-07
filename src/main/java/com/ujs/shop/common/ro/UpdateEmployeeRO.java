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
public class UpdateEmployeeRO {

    private String id;

    @NotBlank(message = "用户名不得为空")
    @Length(message = "用户名最长为{max}个字符", max = 15)
    private String userName;

    @NotBlank(message = "真实姓名不得为空")
    @Length(message = "真实姓名最长为{max}个字符", max = 5)
    private String realName;

    @NotBlank(message = "电话不得为空")
    private String phone;

    @NotNull
    private Boolean gender;

    @NotBlank(message = "身份证号不得为空")
    private String idNumber;

}
