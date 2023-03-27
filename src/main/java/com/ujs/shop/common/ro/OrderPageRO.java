package com.ujs.shop.common.ro;

import com.ujs.shop.common.base.BasePageRO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author mundo.wang
 * @date 2023/3/27 17:13
 */

@Data
public class OrderPageRO extends BasePageRO {

    private String number;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

}
