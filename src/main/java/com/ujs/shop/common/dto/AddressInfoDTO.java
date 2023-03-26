package com.ujs.shop.common.dto;

import lombok.Data;

/**
 * @author mundo.wang
 * @date 2023/3/22 11:47
 */

@Data
public class AddressInfoDTO {

    private String id;

    private String receiver;

    private Boolean gender;

    private String phone;

    private String address;

    private String label;
}
