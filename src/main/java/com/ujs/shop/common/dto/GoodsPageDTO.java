package com.ujs.shop.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author mundo.wang
 * @date 2023/2/18 23:28
 */

@Data
public class GoodsPageDTO {

    private String id;

    private String name;

    private String categoryName;

    private BigDecimal price;

    private String image;

    private Integer allowance;

    private Boolean status;

    private String updateUser;

    @JsonFormat(pattern="yy-MM-dd HH:mm", timezone="GMT+8")
    private LocalDateTime updateTime;
}
