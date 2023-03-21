package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author mundo.wang
 * @date 2023/3/22 0:56
 */


@Data
@TableName("shop_address")
public class AddressPO {

    private String id;

    private String userId;

    private String receiver;

    private Boolean gender;

    private String phone;

    private String address;

    private String label;

    private Boolean isDefault;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic
    private Boolean isDeleted;
}
