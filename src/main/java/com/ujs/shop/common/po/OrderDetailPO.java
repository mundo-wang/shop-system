package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author mundo.wang
 * @date 2023/3/27 13:14
 */


@Data
@TableName("shop_order_detail")
public class OrderDetailPO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String name;

    private String image;

    private String orderId;

    private String goodsId;

    private String packageId;

    private Integer number;

    private BigDecimal amount;

    private LocalDateTime createTime;

    @TableLogic
    private Boolean isDeleted;

}
