package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ujs.shop.common.base.BasePO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:08
 */

@Data
@TableName("shop_package")
public class PackagePO extends BasePO {


    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String categoryId;

    private String name;

    private BigDecimal price;

    private String image;

    private String description;

    private Boolean status;
}
