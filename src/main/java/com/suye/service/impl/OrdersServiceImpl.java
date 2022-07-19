package com.suye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suye.common.BaseContext;
import com.suye.common.CustomException;
import com.suye.entity.*;
import com.suye.mapper.*;
import com.suye.service.OrderDetailService;
import com.suye.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private OrderDetailService orderDetailService;


    /**
     * 用户下单
     * @param orders
     */
    @Override
    @Transactional
    public void submit(Orders orders) {
//        获取当前用户id
        Long userId = BaseContext.getCurrentId();

//        查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectList(queryWrapper);

        if (shoppingCarts == null || shoppingCarts.size() == 0) {
            throw new CustomException("购物车为空，不能下单");
        }

        User user = userMapper.selectById(userId);

        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookMapper.selectById(addressBookId);

        if (addressBook == null) {
            throw new CustomException("用户地址信息有误，不能下单");
        }
        long orderId = IdWorker.getId();

//        原子操作，可以保证线程安全
        AtomicInteger amount = new AtomicInteger(0);

//        遍历购物车数据计算总金额，封装订单明细
        List<OrderDetail> orderDetailList = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

//        向订单表插入数据（一条） 在这之前要填充其他字段
        orders.setNumber(String.valueOf(orderId));
        orders.setOrderTime(new Date());
        orders.setCheckoutTime(new Date());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));  // 总金额
        orders.setUserId(userId);
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));

        this.save(orders);

//        向订单明细表插入数据（一条或多条）
//        mapper没有批量添加功能，如果用mapper只能遍历集合一个一个添加
        orderDetailService.saveBatch(orderDetailList);

//        清空购物车数据
        shoppingCartMapper.delete(queryWrapper);

    }
}




