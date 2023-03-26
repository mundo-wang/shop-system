package com.ujs.shop.common.dto;

import lombok.Data;

/**
 * @author mundo.wang
 * @date 2023/3/22 12:11
 */


@Data
public class AddressDTO {

    private String id;

    private String receiver;

    private Boolean gender;

    private String phone;

    private String address;

    private String label;

    private Boolean isDefault;

}
