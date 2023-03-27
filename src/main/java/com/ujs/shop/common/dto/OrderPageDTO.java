package com.ujs.shop.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/3/27 14:42
 */

@Data
public class OrderPageDTO {


    private String id;

    private Boolean status;

    private BigDecimal amount;

    private List<OrderDetailPageDTO> orderDetails;

}
