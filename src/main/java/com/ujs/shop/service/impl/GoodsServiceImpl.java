package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.*;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.po.CategoryPO;
import com.ujs.shop.common.po.GoodsConfigPO;
import com.ujs.shop.common.po.GoodsPO;
import com.ujs.shop.common.po.PackagePO;
import com.ujs.shop.common.ro.AddGoodsRO;
import com.ujs.shop.common.ro.AddOrUpdateConfigRO;
import com.ujs.shop.common.ro.UpdateGoodsRO;
import com.ujs.shop.mapper.CategoryMapper;
import com.ujs.shop.mapper.GoodsConfigMapper;
import com.ujs.shop.mapper.GoodsMapper;
import com.ujs.shop.mapper.PackageMapper;
import com.ujs.shop.service.GoodsConfigService;
import com.ujs.shop.service.GoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mundo.wang
 * @date 2023/2/12 18:15
 */


@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsPO> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsConfigMapper goodsConfigMapper;

    @Autowired
    private PackageMapper packageMapper;


    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 增加商品的配置
     * @param list
     * @param goodsId
     */
    private void addGoodsConfig(List<AddOrUpdateConfigRO> list, String goodsId) {
        List<GoodsConfigPO> goodsConfigList = list.stream().map(item -> {
            GoodsConfigPO goodsConfigPO = new GoodsConfigPO();
            goodsConfigPO.setId(ConstantBean.getUUID());
            goodsConfigPO.setGoodsId(goodsId);
            BeanUtils.copyProperties(item, goodsConfigPO);
            return goodsConfigPO;
        }).collect(Collectors.toList());

        for (GoodsConfigPO goodsConfigPO : goodsConfigList) {
            goodsConfigMapper.insert(goodsConfigPO);
        }
    }

    /**
     * 拿到所有商品和套餐名
     * @return
     */
    private Set<String> goodsAndPackageNames() {
        List<GoodsPO> list1 = goodsMapper.selectList(null);
        List<PackagePO> list2 = packageMapper.selectList(null);
        Set<String> result = list1.stream().map(GoodsPO::getName).collect(Collectors.toSet());
        Set<String> packageNames = list2.stream().map(PackagePO::getName).collect(Collectors.toSet());
        result.addAll(packageNames);
        return result;
    }

    @Override
    @Transactional
    public void addGoods(AddGoodsRO addGoodsRO) {
        if (goodsAndPackageNames().contains(addGoodsRO.getName())) {
            throw new ServiceException(ResponseCodeEnum.GOODS_NAME_UNIQUE);
        }
        GoodsPO goodsPO = new GoodsPO();
        goodsPO.setId(ConstantBean.getUUID());
        BeanUtils.copyProperties(addGoodsRO, goodsPO);
        goodsMapper.insert(goodsPO);

        List<AddOrUpdateConfigRO> goodsConfigList = addGoodsRO.getGoodsConfig();
        if (goodsConfigList != null) {
            addGoodsConfig(goodsConfigList, goodsPO.getId());
        }
    }

    @Override
    @Transactional
    public void updateGoods(UpdateGoodsRO updateGoodsRO) {
        GoodsPO goodsPO = goodsMapper.selectById(updateGoodsRO.getId());
        if (goodsPO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_GOODS);
        }
        if (! goodsPO.getName().equals(updateGoodsRO.getName())) {
            if (goodsAndPackageNames().contains(updateGoodsRO.getName())) {
                throw new ServiceException(ResponseCodeEnum.GOODS_NAME_UNIQUE);
            }
        }
        BeanUtils.copyProperties(updateGoodsRO, goodsPO);
        goodsMapper.updateById(goodsPO);
        LambdaQueryWrapper<GoodsConfigPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsConfigPO::getGoodsId, goodsPO.getId());
        goodsConfigMapper.delete(wrapper);

        List<AddOrUpdateConfigRO> goodsConfigList = updateGoodsRO.getGoodsConfig();
        if (goodsConfigList != null) {
            addGoodsConfig(goodsConfigList, goodsPO.getId());
        }
    }

    @Override
    @Transactional
    public GoodsInfoDTO getGoodsInfo(String id) {
        GoodsPO goodsPO = goodsMapper.selectById(id);
        if (goodsPO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_GOODS);
        }
        GoodsInfoDTO goodsInfoDTO = new GoodsInfoDTO();
        BeanUtils.copyProperties(goodsPO, goodsInfoDTO);
        LambdaQueryWrapper<GoodsConfigPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsConfigPO::getGoodsId, goodsPO.getId());
        List<GoodsConfigPO> goodsConfigList = goodsConfigMapper.selectList(wrapper);
        List<GoodsConfigDTO> goodsConfigDTOList = goodsConfigList.stream().map(item -> {
            GoodsConfigDTO goodsConfigDTO = new GoodsConfigDTO();
            BeanUtils.copyProperties(item, goodsConfigDTO);
            return goodsConfigDTO;
        }).collect(Collectors.toList());
        goodsInfoDTO.setGoodsConfig(goodsConfigDTOList);
        return goodsInfoDTO;
    }

    @Override
    @Transactional
    public void changeStatus(List<String> goodsIds, Boolean status) {
        for (String id : goodsIds) {
            GoodsPO goodsPO = goodsMapper.selectById(id);
            if (goodsPO == null) {
                throw new ServiceException(ResponseCodeEnum.NO_SUCH_GOODS);
            }
            goodsPO.setStatus(status);
            goodsMapper.updateById(goodsPO);
        }
    }

    @Override
    @Transactional
    public void removeGoods(List<String> goodsIds) {
        for (String id : goodsIds) {
            GoodsPO goodsPO = goodsMapper.selectById(id);
            if (goodsPO == null) {
                throw new ServiceException(ResponseCodeEnum.NO_SUCH_GOODS);
            }
            if (! goodsPO.getStatus()) {
                throw new ServiceException(ResponseCodeEnum.GOODS_ON_SALE);
            }
        }
        LambdaQueryWrapper<GoodsConfigPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(GoodsConfigPO::getGoodsId, goodsIds);
        goodsConfigMapper.delete(wrapper);
        goodsMapper.deleteBatchIds(goodsIds);
    }

    @Override
    public PageFormBean<GoodsPageDTO> goodsPage(Integer page, Integer size, String name, String categoryId) {
        Page<GoodsPageDTO> page1 = new Page<>(page, size);
        IPage<GoodsPageDTO> page2 = goodsMapper.goodsPage(page1, name, categoryId);
        return new PageFormBean<>(page2);
    }

    @Override
    public List<CategoryListDTO> getCategoryList(Boolean categoryType) {
        List<CategoryListDTO> categoryListDTOS = new ArrayList<>();
        LambdaQueryWrapper<CategoryPO> wrapper = new LambdaQueryWrapper<>();
        if (categoryType != null) {
            wrapper.eq(CategoryPO::getType, categoryType);
        }
        wrapper.orderByDesc(CategoryPO::getUpdateTime);
        return categoryMapper.selectList(wrapper).stream().map(
                item -> {
                    CategoryListDTO categoryListDTO = new CategoryListDTO();
                    BeanUtils.copyProperties(item, categoryListDTO);
                    categoryListDTOS.add(categoryListDTO);
                    return categoryListDTO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<GoodsForPackDTO> getGoodsForPack(String categoryId) {
        List<GoodsForPackDTO> dtoList = new ArrayList<>();
        LambdaQueryWrapper<GoodsPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsPO::getCategoryId, categoryId);
        wrapper.eq(GoodsPO::getStatus, false);
        List<GoodsPO> goodsPOList = goodsMapper.selectList(wrapper);
        for (GoodsPO goodsPO : goodsPOList) {
            GoodsForPackDTO goodsForPackDTO = new GoodsForPackDTO();
            BeanUtils.copyProperties(goodsPO, goodsForPackDTO);
            dtoList.add(goodsForPackDTO);
        }
        return dtoList;
    }


    @Override
    public List<GoodsForPackDTO> getGoodsForPackByName(String name) {
        List<GoodsForPackDTO> dtoList = new ArrayList<>();
        LambdaQueryWrapper<GoodsPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(GoodsPO::getName, name);
        List<GoodsPO> goodsPOList = goodsMapper.selectList(wrapper);
        for (GoodsPO goodsPO : goodsPOList) {
            GoodsForPackDTO goodsForPackDTO = new GoodsForPackDTO();
            BeanUtils.copyProperties(goodsPO, goodsForPackDTO);
            dtoList.add(goodsForPackDTO);
        }
        return dtoList;
    }
}
