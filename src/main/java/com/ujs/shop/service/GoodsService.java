package com.ujs.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujs.shop.common.dto.CategoryListDTO;
import com.ujs.shop.common.dto.GoodsInfoDTO;
import com.ujs.shop.common.dto.GoodsPageDTO;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.po.GoodsPO;
import com.ujs.shop.common.ro.AddGoodsRO;
import com.ujs.shop.common.ro.GoodsPageRO;
import com.ujs.shop.common.ro.UpdateGoodsRO;

import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/2/12 18:12
 */
public interface GoodsService extends IService<GoodsPO> {

    void addGoods(AddGoodsRO addGoodsRO);

    void updateGoods(UpdateGoodsRO updateGoodsRO);

    GoodsInfoDTO getGoodsInfo(String id);

    void changeStatus(List<String> goodsIds, Boolean status);

    void removeGoods(List<String> goodsIds);

    PageFormBean<GoodsPageDTO> goodsPage(Integer page, Integer size, String name, String categoryId);

    List<CategoryListDTO> getCategoryList(Boolean categoryType);

}
