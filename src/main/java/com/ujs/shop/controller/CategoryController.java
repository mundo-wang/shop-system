package com.ujs.shop.controller;

import com.ujs.shop.common.base.BaseController;
import com.ujs.shop.common.dto.CategoryInfoDTO;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.AddCategoryRO;
import com.ujs.shop.common.ro.UpdateCategoryRO;
import com.ujs.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mundo.wang
 * @date 2023/2/11 19:24
 */


@RestController
@RequestMapping("/shop/category")
public class CategoryController extends BaseController {


    @Autowired
    private CategoryService categoryService;


    /**
     * 增加分类（商品/套餐）
     * @param addCategoryRO
     * @return
     */
    @PostMapping("/addCategory")
    public ResponseBean<?> addCategory(@RequestBody @Valid AddCategoryRO addCategoryRO) {
        categoryService.addCategory(addCategoryRO);
        return ResponseBean.success();
    }


    /**
     * 编辑分类
     * @param updateCategoryRO
     * @return
     */
    @PostMapping("/updateCategory")
    public ResponseBean<?> updateCategory(@RequestBody @Valid UpdateCategoryRO updateCategoryRO) {
        categoryService.updateCategory(updateCategoryRO);
        return ResponseBean.success();
    }


    /**
     * 删除分类
     * @param id
     * @return
     */
    @GetMapping("/removeCategory")
    public ResponseBean<?> removeCategory(String id) {
        categoryService.removeCategory(id);
        return ResponseBean.success();
    }


    /**
     * 回显分类具体信息
     * @param id
     * @return
     */
    @GetMapping("/getCategoryInfo")
    public ResponseBean<CategoryInfoDTO> getCategoryInfo(String id) {
        CategoryInfoDTO categoryInfo = categoryService.getCategoryInfo(id);
        return ResponseBean.success(categoryInfo);
    }
}
