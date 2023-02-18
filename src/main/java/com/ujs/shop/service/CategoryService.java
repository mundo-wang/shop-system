package com.ujs.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujs.shop.common.dto.CategoryInfoDTO;
import com.ujs.shop.common.dto.CategoryPageDTO;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.po.CategoryPO;
import com.ujs.shop.common.ro.AddCategoryRO;
import com.ujs.shop.common.ro.UpdateCategoryRO;

/**
 * @author mundo.wang
 * @date 2023/2/11 19:19
 *
 * 分类表对应service接口
 */
public interface CategoryService extends IService<CategoryPO> {

    void addCategory(AddCategoryRO addCategoryRO);

    void updateCategory(UpdateCategoryRO updateCategoryRO);

    void removeCategory(String id);

    CategoryInfoDTO getCategoryInfo(String id);

    PageFormBean<CategoryPageDTO> categoryPage(Integer page, Integer size, Boolean type, String name);

}
