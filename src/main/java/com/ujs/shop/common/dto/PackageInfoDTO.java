package com.ujs.shop.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:53
 */


@Data
public class PackageInfoDTO {

    private String id;

    private String categoryId;

    private String name;

    private BigDecimal price;

    private List<PackageGoodsDTO> goodsList;

    private String image;

    private String description;
}
