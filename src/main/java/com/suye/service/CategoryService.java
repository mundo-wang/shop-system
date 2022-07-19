package com.suye.service;

import com.suye.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface CategoryService extends IService<Category> {

    void remove(Long id);
}
