package com.suye.service;

import com.suye.dto.DishDto;
import com.suye.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface DishService extends IService<Dish> {

//    新增菜品，同事插入菜品对应的口味数据
    public void saveWithFlavor(DishDto dishDto);

//    根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

//    更新菜品信息，同时更新口味信息
    void updateWithFlavor(DishDto dishDto);

//    删除菜品，同时删除对应的口味信息
    void deleteDishAndFlavor(List<Long> ids);
}
