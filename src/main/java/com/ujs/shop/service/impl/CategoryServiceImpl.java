package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.CategoryInfoDTO;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.po.CategoryPO;
import com.ujs.shop.common.ro.AddCategoryRO;
import com.ujs.shop.common.ro.UpdateCategoryRO;
import com.ujs.shop.mapper.CategoryMapper;
import com.ujs.shop.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mundo.wang
 * @date 2023/2/11 19:20
 */


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryPO> implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(AddCategoryRO addCategoryRO) {
        CategoryPO categoryPO = new CategoryPO();
        BeanUtils.copyProperties(addCategoryRO, categoryPO);
        categoryPO.setId(ConstantBean.getUUID());
        categoryMapper.insert(categoryPO);
    }

    @Override
    public void updateCategory(UpdateCategoryRO updateCategoryRO) {
        CategoryPO categoryPO = categoryMapper.selectById(updateCategoryRO.getId());
        if (categoryPO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_CATEGORY);
        }
        BeanUtils.copyProperties(updateCategoryRO, categoryPO);
        categoryMapper.updateById(categoryPO);
    }

    @Override
    public void removeCategory(String id) {
        CategoryPO categoryPO = categoryMapper.selectById(id);
        if (categoryPO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_CATEGORY);
        }
        categoryMapper.deleteById(id);
    }

    @Override
    public CategoryInfoDTO getCategoryInfo(String id) {
        CategoryPO categoryPO = categoryMapper.selectById(id);
        if (categoryPO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_CATEGORY);
        }
        CategoryInfoDTO categoryInfoDTO = new CategoryInfoDTO();
        BeanUtils.copyProperties(categoryPO, categoryInfoDTO);
        return categoryInfoDTO;
    }
}
