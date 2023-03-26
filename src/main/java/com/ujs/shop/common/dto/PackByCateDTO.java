package com.ujs.shop.common.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mundo.wang
 * @date 2023/3/26 15:53
 */

@Data
public class PackByCateDTO {

    private String id;

    private String name;

    private Boolean status;

    private BigDecimal price;

    private String image;

    private String description;

    private Boolean type;
}
