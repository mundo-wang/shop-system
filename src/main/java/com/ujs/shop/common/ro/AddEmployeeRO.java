package com.ujs.shop.common.ro;

import lombok.Data;

/**
 * @author mundo.wang
 * @date 2023/2/6 19:04
 */


@Data
public class AddEmployeeRO {

    private String name;

    private String realName;

    private String phone;

    private Boolean gender;

    private String idNumber;
}
