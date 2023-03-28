package com.ujs.shop.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author mundo.wang
 * @date 2023/3/27 17:50
 */


@Data
public class OrderPageBackDTO {

    private String id;

    private String number;

    private Boolean status;

    private String userName;

    private String phone;

    private String address;

    private LocalDateTime createTime;

    private BigDecimal amount;

    private String remark;

}
