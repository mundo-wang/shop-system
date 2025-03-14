package com.ujs.shop.common.ro;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/2/12 19:12
 */


@Data
public class AddGoodsRO {


    @NotBlank(message = "商品名不得为空")
    @Length(message = "商品名最长为{max}个字符", max = 15)
    private String name;

    @NotBlank(message = "关联的分类id不能为空")
    private String categoryId;

    @NotNull(message = "价格不得为空")
    private BigDecimal price;

    @NotNull(message = "商品余量不得为空")
    @Range(message = "商品余量必须在{min}到{max}之间", min = 10, max = 1000)
    private Integer allowance;

    private List<AddOrUpdateConfigRO> goodsConfig;

    private String image;

    private String description;

}
