package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ujs.shop.common.base.BasePO;
import lombok.Data;

/**
 * @author mundo.wang
 * @date 2023/2/19 15:17
 */


@Data
@TableName("shop_customer")
public class CustomerPO extends BasePO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userName;

    private String phone;

    private Boolean gender;

    private String picture;

    private Boolean status;

}
