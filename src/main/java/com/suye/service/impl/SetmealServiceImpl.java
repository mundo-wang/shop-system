package com.suye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suye.common.CustomException;
import com.suye.common.R;
import com.suye.dto.SetmealDto;
import com.suye.entity.Category;
import com.suye.entity.Dish;
import com.suye.entity.Setmeal;
import com.suye.entity.SetmealDish;
import com.suye.mapper.CategoryMapper;
import com.suye.service.CategoryService;
import com.suye.service.SetmealDishService;
import com.suye.service.SetmealService;
import com.suye.mapper.SetmealMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealDishService setmealDishService;


    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
//        保存套餐的基本信息，操作setmeal，执行insert
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(String.valueOf(setmealDto.getId()));
            return item;
        }).collect(Collectors.toList());

//        保存套餐和菜品的关联信息，操作setmeal_dish，执行insert
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     * @param ids
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
//        查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
//        查出来几条符合数据的（符合状态为1的）
        int count = this.count(queryWrapper);

//        如果不能删除，抛出一个业务异常
        if (count > 0) {
            throw new CustomException("套餐正在售卖，不能删除");
        }

//        如果可以删除，先删除套餐表中数据
        this.removeByIds(ids);

//        删除关系表中数据（操作setmeal_dish表）
//        delete from setmeal_dish where setmeal_id in (1, 2, 3)
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(wrapper);

    }

    /**
     * 点击修改时，回显菜品
     * @param id
     * @return
     */
    @Override
    @Transactional
    public SetmealDto getByIdWithDish(Long id) {
        Setmeal setmeal = this.getById(id);

        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
//        拿到对应的菜品列表
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);

//        拿到对应的套餐分类（通过category的id拿到name）
        Category category = categoryMapper.selectById(setmeal.getCategoryId());
        setmealDto.setCategoryName(category.getName());

        return setmealDto;
    }


    /**
     * 更新套餐，同时更新套餐和菜品的关联关系
     * @param setmealDto
     */
    @Override
    @Transactional
    public void updateWithDish(SetmealDto setmealDto) {
//        更新基本信息
        this.updateById(setmealDto);

//        拿到更新后的菜品信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

//        给菜品添加上和套餐的对应信息
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(String.valueOf(setmealDto.getId()));
            return item;
        }).collect(Collectors.toList());

//        删除套餐对应的菜品
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(queryWrapper);

//        添加更新后的菜品
        setmealDishService.saveBatch(setmealDishes);

    }
}




