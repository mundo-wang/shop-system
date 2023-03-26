package com.ujs.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujs.shop.common.dto.CartListDTO;
import com.ujs.shop.common.po.CartPO;
import com.ujs.shop.common.ro.AddCartRO;
import com.ujs.shop.common.ro.SubCartRO;

import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/3/26 19:36
 */
public interface CartService extends IService<CartPO> {

    Integer addCart(AddCartRO addCartRO, String userId);

    List<CartListDTO> cartList(String userId);

    Integer subCart(SubCartRO subCartRO, String userId);

    void cleanCart(String userId);

}
