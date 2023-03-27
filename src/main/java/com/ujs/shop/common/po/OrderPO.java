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
 * @date 2023/3/27 13:01
 */

@Data
@TableName("shop_order")
public class OrderPO {


    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String addressId;

    private String userId;

    /**
     * 订单编号
     */
    private String number;

    /**
     * 0 为待处理，1 为已处理
     */
    private Boolean status;

    private BigDecimal amount;

    private String remark;

    private LocalDateTime createTime;

    @TableLogic
    private Boolean isDeleted;


}
