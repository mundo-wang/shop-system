package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ujs.shop.common.base.BasePO;
import lombok.Data;


/**
 * @author mundo.wang
 * @date 2023/2/12 17:47
 */


@Data
@TableName("shop_goods_config")
public class GoodsConfigPO extends BasePO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;


    /**
     * 商品配置关联的商品
     */
    private String goodsId;

    private String name;

    private String value;

}
