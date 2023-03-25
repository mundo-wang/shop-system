package com.ujs.shop.common.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mundo.wang
 * @date 2023/3/24 22:04
 */


@Data
public class GoodsForPackDTO {

    private String id;

    private String name;

    private Boolean status;

    private BigDecimal price;
}
