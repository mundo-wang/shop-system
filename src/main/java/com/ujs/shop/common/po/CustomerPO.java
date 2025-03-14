package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author mundo.wang
 * @date 2023/2/19 15:17
 */


@Data
@TableName("shop_customer")
public class CustomerPO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userName;

    private String phone;

    private Boolean gender;

    private String picture;

    private Boolean status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
