package com.ujs.shop.common.ro;

import lombok.Data;

/**
 * @author mundo.wang
 * @date 2023/2/20 23:37
 */


@Data
public class UpdateCustomerRO {

    private String id;

    private String userName;

    private Boolean gender;

    private String picture;

}
