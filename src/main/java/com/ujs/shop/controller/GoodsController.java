package com.ujs.shop.controller;

import com.ujs.shop.common.base.BaseController;
import com.ujs.shop.common.dto.GoodsInfoDTO;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.AddGoodsRO;
import com.ujs.shop.common.ro.UpdateGoodsRO;
import com.ujs.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mundo.wang
 * @date 2023/2/12 18:17
 *
 * 商品表对应controller
 */


@RestController
@RequestMapping("/shop/goods")
public class GoodsController extends BaseController {


    @Autowired
    private GoodsService goodsService;


    /**
     * 增加商品
     * @param addGoodsRO
     * @return
     */
    @PostMapping("/addGoods")
    public ResponseBean<?> addGoods(@Valid @RequestBody AddGoodsRO addGoodsRO) {
        return null;
    }


    /**
     * 修改商品
     * @param updateGoodsRO
     * @return
     */
    @PostMapping("/updateGoods")
    public ResponseBean<?> updateGoods(@Valid @RequestBody UpdateGoodsRO updateGoodsRO) {
        return null;
    }


    /**
     * 回显商品信息
     * @param id
     * @return
     */
    @GetMapping("/getGoodsInfo")
    public ResponseBean<GoodsInfoDTO> getGoodsInfo(String id) {
        return null;
    }

}
