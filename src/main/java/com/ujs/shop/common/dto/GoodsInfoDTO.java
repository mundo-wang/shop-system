package com.ujs.shop.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/2/12 19:31
 */

@Data
public class GoodsInfoDTO {

    private String id;

    private String name;

    private String categoryId;

    private BigDecimal price;

    private Integer allowance;

    private List<GoodsConfigDTO> goodsConfig;

    private String image;

    private String description;
}
