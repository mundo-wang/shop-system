package com.ujs.shop.common.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mundo.wang
 * @date 2023/3/27 11:17
 */

@Data
public class GetGoodsDTO {

    private String id;

    private String name;

    private BigDecimal price;

    private String image;

    private Integer number;
}
