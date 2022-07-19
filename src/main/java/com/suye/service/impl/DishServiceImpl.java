package com.suye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suye.common.CustomException;
import com.suye.dto.DishDto;
import com.suye.entity.Dish;
import com.suye.entity.DishFlavor;
import com.suye.service.DishFlavorService;
import com.suye.service.DishService;
import com.suye.mapper.DishMapper;
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
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     */
    @Override
    @Transactional   // 这个方法涉及到多张表操作，所以需要加上事务注解控制
    public void saveWithFlavor(DishDto dishDto) {
//        保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId(); // 菜品id

        List<DishFlavor> flavors = dishDto.getFlavors();  // 菜品口味

//        给每个口味信息赋上id值，建立对应关系，每个item就是flavors集合的一个元素
        flavors = flavors.stream().map((item) ->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

//        保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @Override
    @Transactional
    public DishDto getByIdWithFlavor(Long id) {
//        查询菜品基本信息，从dish表查询
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

//        查询当前菜品的口味信息，从dish_flavor查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
//        查出对应的口味数据
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
//        更新dish表基本信息
        this.updateById(dishDto);

//        清理当前菜品对应口味数据（dish_flavor的delete）
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
//        这个地方要关闭dish_flavor表的逻辑删除
        dishFlavorService.remove(queryWrapper);

//        添加当前提交过来的口味数据
        List<DishFlavor> flavors = dishDto.getFlavors();

//        flavors里只有name和value属性有值，所以我们需要给它的id也赋上值
        flavors = flavors.stream().map((item) ->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Override
    @Transactional
    public void deleteDishAndFlavor(List<Long> ids) {
//        通过ids和状态为1先查到所要删除的有没有在售商品
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        queryWrapper.eq(Dish::getStatus, 1);
        int count = this.count(queryWrapper);
        if (count > 0) {
            throw new CustomException("菜品正在售卖，不能删除");
        }

//        删除菜品表数据
        this.removeByIds(ids);
//        删除对应口味表数据
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(wrapper);

    }

}




