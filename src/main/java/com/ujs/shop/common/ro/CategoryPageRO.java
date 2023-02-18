package com.ujs.shop.common.ro;

import com.ujs.shop.common.base.BasePageRO;
import lombok.Data;

/**
 * @author mundo.wang
 * @date 2023/2/18 22:16
 */


@Data
public class CategoryPageRO extends BasePageRO {

    private Boolean type;

    private String name;

}
