package com.ujs.shop.common.ro;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author mundo.wang
 * @date 2023/2/11 19:38
 */


@Data
public class AddCategoryRO {


    @NotBlank(message = "分类名不得为空")
    @Length(message = "分类名最长为{max}个字符", max = 15)
    private String name;

    @NotNull(message = "分类类型不得为空")
    private Boolean type;


}
