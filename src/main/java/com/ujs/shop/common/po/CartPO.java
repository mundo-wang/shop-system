package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author mundo.wang
 * @date 2023/3/26 17:23
 */

@Data
@TableName("shop_cart")
public class CartPO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;

    private String goodsId;

    private String packageId;

    private String goodsConfig;

    private String name;

    private String image;

    private Integer number;

    private BigDecimal amount;

    private LocalDateTime createTime;
    
}
