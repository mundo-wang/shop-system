package com.suye.service;

import com.suye.dto.SetmealDto;
import com.suye.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     * @param ids
     */
    void removeWithDish(List<Long> ids);


    /**
     * 查询套餐，页面回显
     * @param id
     * @return
     */
    SetmealDto getByIdWithDish(Long id);

    /**
     * 更新套餐，同时更新套餐和菜品的关联关系
     * @param setmealDto
     */
    void updateWithDish(SetmealDto setmealDto);
}
