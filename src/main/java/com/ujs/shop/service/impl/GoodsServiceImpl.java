package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.GoodsConfigDTO;
import com.ujs.shop.common.dto.GoodsInfoDTO;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.po.GoodsConfigPO;
import com.ujs.shop.common.po.GoodsPO;
import com.ujs.shop.common.ro.AddGoodsRO;
import com.ujs.shop.common.ro.AddOrUpdateConfigRO;
import com.ujs.shop.common.ro.UpdateGoodsRO;
import com.ujs.shop.mapper.GoodsConfigMapper;
import com.ujs.shop.mapper.GoodsMapper;
import com.ujs.shop.service.GoodsConfigService;
import com.ujs.shop.service.GoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void addGoods(AddGoodsRO addGoodsRO) {
        GoodsPO goodsPO = new GoodsPO();
        goodsPO.setId(ConstantBean.getUUID());
        BeanUtils.copyProperties(addGoodsRO, goodsPO);
        goodsMapper.insert(goodsPO);

        List<AddOrUpdateConfigRO> goodsConfigList = addGoodsRO.getGoodsConfig();
        addGoodsConfig(goodsConfigList, goodsPO.getId());
    }

    @Override
    @Transactional
    public void updateGoods(UpdateGoodsRO updateGoodsRO) {
        GoodsPO goodsPO = goodsMapper.selectById(updateGoodsRO.getId());
        if (goodsPO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_GOODS);
        }
        BeanUtils.copyProperties(updateGoodsRO, goodsPO);
        goodsMapper.updateById(goodsPO);
        LambdaQueryWrapper<GoodsConfigPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsConfigPO::getGoodsId, goodsPO.getId());
        goodsConfigMapper.delete(wrapper);

        List<AddOrUpdateConfigRO> goodsConfigList = updateGoodsRO.getGoodsConfig();
        addGoodsConfig(goodsConfigList, goodsPO.getId());
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
}
