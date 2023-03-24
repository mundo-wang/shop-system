package com.ujs.shop.common.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:58
 */


@Data
public class PackageGoodsDTO {

    private String name;

    private BigDecimal price;

    private Integer amount;
}
