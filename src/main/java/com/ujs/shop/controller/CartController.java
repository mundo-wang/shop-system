package com.ujs.shop.controller;

import com.ujs.shop.common.base.BaseController;
import com.ujs.shop.common.dto.CartListDTO;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.AddCartRO;
import com.ujs.shop.common.ro.SubCartRO;
import com.ujs.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/3/26 19:41
 */


@RestController
@RequestMapping("/shop/cart")
public class CartController extends BaseController {


    @Autowired
    private CartService cartService;


    /**
     * 添加购物车
     *
     * @param addCartRO
     * @return
     */
    @PostMapping("/addCart")
    public ResponseBean<Integer> addCart(@Valid @RequestBody AddCartRO addCartRO) {
        Integer number = cartService.addCart(addCartRO, getCustomerId());
        return ResponseBean.success(number);
    }


    /**
     * 购物车列表
     *
     * @return
     */
    @GetMapping("/cartList")
    public ResponseBean<List<CartListDTO>> cartList() {
        List<CartListDTO> cartListDTOS = cartService.cartList(getCustomerId());
        return ResponseBean.success(cartListDTOS);
    }


    @PostMapping("/subCart")
    public ResponseBean<Integer> subCart(@RequestBody SubCartRO subCartRO) {
        Integer number = cartService.subCart(subCartRO, getCustomerId());
        return ResponseBean.success(number);
    }


    @GetMapping("/cleanCart")
    public ResponseBean<?> cleanCart() {
        cartService.cleanCart(getCustomerId());
        return ResponseBean.success();
    }


}
