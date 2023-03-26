package com.ujs.shop.common.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mundo.wang
 * @date 2023/3/26 21:10
 */


@Data
public class CartListDTO {

    private String id;

    private String goodsId;

    private String packageId;

    private String name;

    private String image;

    private Integer number;

    private BigDecimal amount;

}
