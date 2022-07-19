package com.suye.service;

import com.suye.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface OrdersService extends IService<Orders> {

    void submit(Orders orders);

}
