package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.CategoryInfoDTO;
import com.ujs.shop.common.dto.CategoryPageDTO;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.po.CategoryPO;
import com.ujs.shop.common.po.GoodsPO;
import com.ujs.shop.common.po.PackagePO;
import com.ujs.shop.common.ro.AddCategoryRO;
import com.ujs.shop.common.ro.UpdateCategoryRO;
import com.ujs.shop.mapper.CategoryMapper;
import com.ujs.shop.mapper.GoodsMapper;
import com.ujs.shop.mapper.PackageMapper;
import com.ujs.shop.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/2/11 19:20
 */


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryPO> implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private PackageMapper packageMapper;

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
        LambdaQueryWrapper<GoodsPO> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(GoodsPO::getCategoryId, id);
        List<GoodsPO> goodsPOList = goodsMapper.selectList(wrapper1);
        LambdaQueryWrapper<PackagePO> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(PackagePO::getCategoryId, id);
        List<PackagePO> packagePOS = packageMapper.selectList(wrapper2);
        if (goodsPOList.size() != 0 || packagePOS.size() != 0) {
            throw new ServiceException(ResponseCodeEnum.HAS_GOODS_OR_PACKAGE);
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

    @Override
    public PageFormBean<CategoryPageDTO> categoryPage(Integer page, Integer size, Boolean type, String name) {
        Page<CategoryPageDTO> page1 = new Page<>(page, size);
        IPage<CategoryPageDTO> page2 = categoryMapper.categoryPage(page1, type, name);
        return new PageFormBean<>(page2);
    }
}
