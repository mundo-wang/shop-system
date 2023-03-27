package com.ujs.shop.controller;

import com.ujs.shop.common.base.BaseController;
import com.ujs.shop.common.dto.OrderPageDTO;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.SubmitRO;
import com.ujs.shop.service.OrderDetailService;
import com.ujs.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/3/27 13:41
 */


@RestController
@RequestMapping("/shop/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public ResponseBean<?> submit(@RequestBody SubmitRO submitRO) {
        orderService.submit(submitRO, getCustomerId());
        return ResponseBean.success();
    }


    @GetMapping("/orderPage")
    public ResponseBean<PageFormBean<OrderPageDTO>> orderPage(@RequestParam Integer page, @RequestParam Integer size) {
        PageFormBean<OrderPageDTO> orderPage = orderService.orderPage(page, size, getCustomerId());
        return ResponseBean.success(orderPage);
    }


    @GetMapping("/orderList")
    public ResponseBean<List<OrderPageDTO>> orderList() {
        List<OrderPageDTO> orderPageDTOS = orderService.orderList(getCustomerId());
        return ResponseBean.success(orderPageDTOS);
    }

}
