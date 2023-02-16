package com.ujs.shop.common.dto;

import com.ujs.shop.common.ro.AddOrUpdateGoodsRO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:53
 */


@Data
public class PackageInfoDTO {

    private String categoryId;

    private String name;

    private BigDecimal price;

    private List<PackageGoodsDTO> goodsList;

    private String image;

    private String description;
}
