package com.ujs.shop.common.ro;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:42
 */

@Data
public class AddOrUpdateGoodsRO {

    private String name;

    private String categoryId;

    @Range(message = "商品数量必须在{min}和{max}之间", min = 1, max = 20)
    private Integer amount;
}
