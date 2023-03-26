package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.CartListDTO;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.po.CartPO;
import com.ujs.shop.common.ro.AddCartRO;
import com.ujs.shop.common.ro.SubCartRO;
import com.ujs.shop.mapper.CartMapper;
import com.ujs.shop.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/3/26 19:39
 */


@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, CartPO> implements CartService {


    @Autowired
    private CartMapper cartMapper;

    @Override
    public Integer addCart(AddCartRO addCartRO, String userId) {
        LambdaQueryWrapper<CartPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CartPO::getUserId, userId);
        if (addCartRO.getGoodsId() != null) {
            wrapper.eq(CartPO::getGoodsId, addCartRO.getGoodsId());
        } else {
            wrapper.eq(CartPO::getPackageId, addCartRO.getPackageId());
        }
        CartPO cartPO = cartMapper.selectOne(wrapper);
        if (cartPO == null) {
            cartPO = new CartPO();
            cartPO.setId(ConstantBean.getUUID());
            cartPO.setUserId(userId);
            cartPO.setNumber(1);
            cartPO.setCreateTime(LocalDateTime.now());
            BeanUtils.copyProperties(addCartRO, cartPO);
            cartMapper.insert(cartPO);
        } else {
            cartPO.setNumber(cartPO.getNumber() + 1);
            cartMapper.updateById(cartPO);
        }
        return cartPO.getNumber();
    }

    @Override
    public List<CartListDTO> cartList(String userId) {
        List<CartListDTO> listDTOS = new ArrayList<>();
        List<CartPO> cartPOS = cartMapper.selectList(new LambdaQueryWrapper<CartPO>()
                .eq(CartPO::getUserId, userId)
                .orderByDesc(CartPO::getCreateTime));
        for (CartPO cartPO : cartPOS) {
            CartListDTO cartListDTO = new CartListDTO();
            BeanUtils.copyProperties(cartPO, cartListDTO);
            listDTOS.add(cartListDTO);
        }
        return listDTOS;
    }

    @Override
    public Integer subCart(SubCartRO subCartRO, String userId) {
        LambdaQueryWrapper<CartPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CartPO::getUserId, userId);
        if (subCartRO.getGoodsId() != null) {
            wrapper.eq(CartPO::getGoodsId, subCartRO.getGoodsId());
        } else {
            wrapper.eq(CartPO::getPackageId, subCartRO.getPackageId());
        }
        CartPO cartPO = cartMapper.selectOne(wrapper);
        if (cartPO.getNumber() > 1) {
            cartPO.setNumber(cartPO.getNumber() - 1);
            cartMapper.updateById(cartPO);
            return cartPO.getNumber();
        } else {
            cartMapper.deleteById(cartPO.getId());
            return 0;
        }
    }

    @Override
    public void cleanCart(String userId) {
        LambdaQueryWrapper<CartPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CartPO::getUserId, userId);
        cartMapper.delete(wrapper);
    }
}
