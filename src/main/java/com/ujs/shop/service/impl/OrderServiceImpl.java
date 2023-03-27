package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.OrderDetailPageDTO;
import com.ujs.shop.common.dto.OrderPageBackDTO;
import com.ujs.shop.common.dto.OrderPageDTO;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.po.CartPO;
import com.ujs.shop.common.po.OrderDetailPO;
import com.ujs.shop.common.po.OrderPO;
import com.ujs.shop.common.ro.OrderPageRO;
import com.ujs.shop.common.ro.SubmitRO;
import com.ujs.shop.mapper.CartMapper;
import com.ujs.shop.mapper.OrderDetailMapper;
import com.ujs.shop.mapper.OrderMapper;
import com.ujs.shop.service.OrderService;
import com.ujs.shop.utils.ValidateCodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/3/27 13:35
 */

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderPO> implements OrderService {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private CartMapper cartMapper;


    @Override
    @Transactional
    public void submit(SubmitRO submitRO, String userId) {
        OrderPO orderPO = new OrderPO();
        orderPO.setId(ConstantBean.getUUID());
        orderPO.setNumber(orderPO.getId().substring(0, 8));
        BeanUtils.copyProperties(submitRO, orderPO);
        orderPO.setStatus(false);
        orderPO.setUserId(userId);
        orderPO.setCreateTime(LocalDateTime.now());
        LambdaQueryWrapper<CartPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CartPO::getUserId, userId);
        wrapper.orderByDesc(CartPO::getCreateTime);
        List<CartPO> cartPOS = cartMapper.selectList(wrapper);
        BigDecimal amount = new BigDecimal(0);
        for (CartPO cartPO : cartPOS) {
            BigDecimal temp = cartPO.getAmount().multiply(new BigDecimal(cartPO.getNumber()));
            amount = amount.add(temp);
            OrderDetailPO orderDetailPO = new OrderDetailPO();
            orderDetailPO.setOrderId(orderPO.getId());
            orderDetailPO.setCreateTime(LocalDateTime.now());
            BeanUtils.copyProperties(cartPO, orderDetailPO);
            orderDetailPO.setId(ConstantBean.getUUID());
            orderDetailMapper.insert(orderDetailPO);
        }
        orderPO.setAmount(amount);
        orderMapper.insert(orderPO);
        cartMapper.delete(wrapper);
    }

    @Override
    public PageFormBean<OrderPageDTO> orderPage(Integer page, Integer size, String userId) {
        Page<OrderPageDTO> page1 = new Page<>(page, size);
        IPage<OrderPageDTO> page2 = orderMapper.orderPage(page1, userId);
        return new PageFormBean<>(page2);
    }

    @Override
    @Transactional
    public List<OrderPageDTO> orderList(String userId) {
        List<OrderPageDTO> list = new ArrayList<>();
        LambdaQueryWrapper<OrderPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderPO::getUserId, userId).orderByDesc(OrderPO::getCreateTime);
        List<OrderPO> orderPOS = orderMapper.selectList(wrapper);
        for (OrderPO orderPO : orderPOS) {
            OrderPageDTO orderPageDTO = new OrderPageDTO();
            BeanUtils.copyProperties(orderPO, orderPageDTO);
            LambdaQueryWrapper<OrderDetailPO> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(OrderDetailPO::getOrderId, orderPO.getId());
            wrapper1.orderByDesc(OrderDetailPO::getCreateTime);
            List<OrderDetailPO> list1 = orderDetailMapper.selectList(wrapper1);
            orderPageDTO.setOrderDetails(new ArrayList<OrderDetailPageDTO>());
            for (OrderDetailPO orderDetailPO : list1) {
                OrderDetailPageDTO orderDetailPageDTO = new OrderDetailPageDTO();
                BeanUtils.copyProperties(orderDetailPO, orderDetailPageDTO);
                orderPageDTO.getOrderDetails().add(orderDetailPageDTO);
            }
            list.add(orderPageDTO);
        }
        return list;
    }

    @Override
    public PageFormBean<OrderPageBackDTO> getOrderPage(OrderPageRO orderPageRO) {
        Page<OrderPageBackDTO> page1 = new Page<>(orderPageRO.getPage(), orderPageRO.getSize());
        IPage<OrderPageBackDTO> page2 = orderMapper.getOrderPage(page1,
                orderPageRO.getNumber(),
                orderPageRO.getBeginTime(),
                orderPageRO.getEndTime());
        return new PageFormBean<>(page2);
    }

    @Override
    public void changeStatus(String id) {
        OrderPO orderPO = orderMapper.selectById(id);
        if (orderPO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_ORDER);
        }
        orderPO.setStatus(true);
        orderMapper.updateById(orderPO);
    }
}
