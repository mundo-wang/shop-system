package com.ujs.shop.common.ro;

import com.ujs.shop.common.base.BasePageRO;
import lombok.Data;

/**
 * @author mundo.wang
 * @date 2023/2/18 23:34
 */


@Data
public class GoodsPageRO extends BasePageRO {

    private String name;

    private String categoryId;

}
