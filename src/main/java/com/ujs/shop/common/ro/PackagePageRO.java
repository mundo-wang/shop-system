package com.ujs.shop.common.ro;

import com.ujs.shop.common.base.BasePageRO;
import lombok.Data;

/**
 * @author mundo.wang
 * @date 2023/2/19 12:13
 */


@Data
public class PackagePageRO extends BasePageRO {

    private String name;

    private String categoryId;
}
