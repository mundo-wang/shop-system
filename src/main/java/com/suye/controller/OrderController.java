package com.suye.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suye.common.BaseContext;
import com.suye.common.R;
import com.suye.entity.OrderDetail;
import com.suye.entity.Orders;
import com.suye.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sj.w
 * @date 2022/7/7 10:55
 */

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;


    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("订单数据：{}", orders.toString());
        ordersService.submit(orders);
        return R.success("下单成功");
    }


    @GetMapping("/userPage")
    public R<Page> userPage(Integer page, Integer pageSize) {
//        获取订单用户
        Long currentId = BaseContext.getCurrentId();


        return null;
    }
}
