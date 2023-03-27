package com.ujs.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujs.shop.common.dto.OrderPageBackDTO;
import com.ujs.shop.common.dto.OrderPageDTO;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.po.OrderPO;
import com.ujs.shop.common.ro.OrderPageRO;
import com.ujs.shop.common.ro.SubmitRO;

import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/3/27 13:34
 */
public interface OrderService extends IService<OrderPO> {

    void submit(SubmitRO submitRO, String userId);

    PageFormBean<OrderPageDTO> orderPage(Integer page, Integer size, String userId);

    List<OrderPageDTO> orderList(String userId);

    PageFormBean<OrderPageBackDTO> getOrderPage(OrderPageRO orderPageRO);

    void changeStatus(String id);
}
