package com.ujs.shop.common.ro;

import lombok.Data;

import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/2/13 9:54
 */


@Data
public class ChangeGoodsStatusRO {

    private List<String> goodsIds;

    private Boolean status;
}
