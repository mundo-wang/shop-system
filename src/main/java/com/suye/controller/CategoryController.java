package com.suye.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suye.common.R;
import com.suye.entity.Category;
import com.suye.service.CategoryService;
import com.suye.service.DishService;
import com.suye.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sj.w
 */

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }


    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);

//        条件构造器对象
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
//        添加排序条件，根素sort字段升序排序
        queryWrapper.orderByAsc(Category::getSort);

//        进行分页查询
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }


    @DeleteMapping
    public R<String> delete(Long id) {
        log.info("删除分类，id = {}", id);
//        categoryService.removeById(id);
//        在impl类中自定义的remove方法
        categoryService.remove(id);

        return R.success("分类信息删除成功");
    }


    /**
     * 根据id修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分类信息：" + category.toString());
        categoryService.updateById(category);

        return R.success("修改分类信息成功");
    }


    /**
     * 根据条件查询分类数据（显示在下拉列表）
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
//        条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
//        添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);
    }
}
