package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ujs.shop.common.base.BasePO;
import lombok.Data;

/**
 * @author mundo.wang
 * @date 2023/2/11 19:11
 * <p>
 * <p>
 * 分类表对应实体类
 */


@Data
@TableName("shop_category")
public class CategoryPO extends BasePO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String name;


    /**
     * false为商品分类，true为套餐分类
     */
    private Boolean type;


}
