package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:20
 */


@Data
@TableName("shop_package_detail")
public class PackageDetailPO {


    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String packageId;

    private String goodsId;

    private Integer amount;

}
