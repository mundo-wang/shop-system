package com.suye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suye.common.CustomException;
import com.suye.entity.Category;
import com.suye.entity.Dish;
import com.suye.entity.Setmeal;
import com.suye.service.CategoryService;
import com.suye.mapper.CategoryMapper;
import com.suye.service.DishService;
import com.suye.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id进行删除，在删除前需要进行判断
     * @param id
     */

    @Override
    public void remove(Long id) {


//        查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if (count1 > 0) {
//            已经关联菜品，需要抛出业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

//        查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
//            已经关联套餐，需要抛出业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

//        正常删除分类
        super.removeById(id);

    }
}




