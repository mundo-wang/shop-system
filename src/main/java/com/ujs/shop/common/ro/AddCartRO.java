package com.ujs.shop.common.ro;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mundo.wang
 * @date 2023/3/26 20:05
 */


@Data
public class AddCartRO {

    private String name;

    private String image;

    private String goodsId;

    private String packageId;

    private BigDecimal amount;
}
