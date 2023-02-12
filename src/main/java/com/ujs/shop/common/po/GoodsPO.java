package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ujs.shop.common.base.BasePO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mundo.wang
 * @date 2023/2/12 17:54
 */


@Data
@TableName("shop_goods")
public class GoodsPO extends BasePO {


    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String name;

    /**
     * 商品关联的类型
     */
    private String categoryId;

    private BigDecimal price;

    private String image;

    private String description;

    /**
     * 0为停售，1为启售
     */
    private Boolean status;

}
