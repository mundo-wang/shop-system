package com.ujs.shop.common.dto;

import lombok.Data;

/**
 * @author mundo.wang
 * @date 2023/3/22 23:17
 */


@Data
public class EmployeeLoginDTO extends EmployeeInfoDTO{

    private String jwtToken;

}
