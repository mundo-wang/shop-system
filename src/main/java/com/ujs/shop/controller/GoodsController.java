package com.ujs.shop.controller;

import com.ujs.shop.common.base.BaseController;
import com.ujs.shop.common.dto.CategoryListDTO;
import com.ujs.shop.common.dto.GoodsForPackDTO;
import com.ujs.shop.common.dto.GoodsInfoDTO;
import com.ujs.shop.common.dto.GoodsPageDTO;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.AddGoodsRO;
import com.ujs.shop.common.ro.ChangeGoodsStatusRO;
import com.ujs.shop.common.ro.GoodsPageRO;
import com.ujs.shop.common.ro.UpdateGoodsRO;
import com.ujs.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/2/12 18:17
 * <p>
 * 商品表对应controller
 */


@RestController
@RequestMapping("/shop/goods")
public class GoodsController extends BaseController {


    @Autowired
    private GoodsService goodsService;


    /**
     * 增加商品
     *
     * @param addGoodsRO
     * @return
     */
    @PostMapping("/addGoods")
    public ResponseBean<?> addGoods(@Valid @RequestBody AddGoodsRO addGoodsRO) {
        goodsService.addGoods(addGoodsRO);
        return ResponseBean.success();
    }


    /**
     * 修改商品
     *
     * @param updateGoodsRO
     * @return
     */
    @PostMapping("/updateGoods")
    public ResponseBean<?> updateGoods(@Valid @RequestBody UpdateGoodsRO updateGoodsRO) {
        goodsService.updateGoods(updateGoodsRO);
        return ResponseBean.success();
    }


    /**
     * 回显商品信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getGoodsInfo")
    public ResponseBean<GoodsInfoDTO> getGoodsInfo(@RequestParam String id) {
        GoodsInfoDTO goodsInfo = goodsService.getGoodsInfo(id);
        return ResponseBean.success(goodsInfo);
    }


    /**
     * 单体/批量 启售/停售
     *
     * @param statusRO
     * @return
     */
    @PostMapping("/changeStatus")
    public ResponseBean<?> changeStatus(@Valid @RequestBody ChangeGoodsStatusRO statusRO) {
        List<String> goodsIds = Arrays.asList(statusRO.getId().split(","));
        goodsService.changeStatus(goodsIds, statusRO.getStatus());
        return ResponseBean.success();
    }


    /**
     * 单体/批量 删除商品以及对应配置
     *
     * @param goodsIds
     * @return
     */
    @GetMapping("/removeGoods")
    public ResponseBean<?> removeGoods(@RequestParam("goodsIds") List<String> goodsIds) {
        goodsService.removeGoods(goodsIds);
        return ResponseBean.success();
    }


    /**
     * 商品分页展示
     *
     * @param goodsPageRO
     * @return
     */
    @PostMapping("/goodsPage")
    public ResponseBean<PageFormBean<GoodsPageDTO>> goodsPage(@Valid @RequestBody GoodsPageRO goodsPageRO) {
        PageFormBean<GoodsPageDTO> goodsPage = goodsService.goodsPage(
                goodsPageRO.getPage(),
                goodsPageRO.getSize(),
                goodsPageRO.getName(),
                goodsPageRO.getCategoryId()
        );
        return ResponseBean.success(goodsPage);
    }


    /**
     * 获取分类下拉框
     *
     * @param categoryType
     * @return
     */
    @GetMapping("/getCategoryList")
    public ResponseBean<List<CategoryListDTO>> getCategoryList(Boolean categoryType) {
        List<CategoryListDTO> categoryList = goodsService.getCategoryList(categoryType);
        return ResponseBean.success(categoryList);
    }


    /**
     * 通过分类查所有商品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/getGoodsForPack")
    public ResponseBean<List<GoodsForPackDTO>> getGoodsForPack(@RequestParam String categoryId) {
        List<GoodsForPackDTO> goodsForPack = goodsService.getGoodsForPack(categoryId);
        return ResponseBean.success(goodsForPack);
    }


    /**
     * 通过名字查商品
     *
     * @param name
     * @return
     */
    @GetMapping("/getGoodsForPackByName")
    public ResponseBean<List<GoodsForPackDTO>> getGoodsForPackByName(@RequestParam String name) {
        List<GoodsForPackDTO> goodsForPack = goodsService.getGoodsForPackByName(name);
        return ResponseBean.success(goodsForPack);
    }

}
