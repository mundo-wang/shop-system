package com.ujs.shop.common.ro;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author mundo.wang
 * @date 2023/2/11 19:45
 */

@Data
public class UpdateCategoryRO {

    private String id;


    @NotBlank(message = "分类名不得为空")
    @Length(message = "分类名最长为{max}个字符", max = 15)
    private String name;
}
