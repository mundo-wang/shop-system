package com.ujs.shop.controller;

import com.ujs.shop.common.base.BaseController;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.UpdateCustomerRO;
import com.ujs.shop.common.ro.UpdatePackageRO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mundo.wang
 * @date 2023/2/19 15:29
 */


@RestController
@RequestMapping("/shop/customer")
public class CustomerController extends BaseController {


    /**
     * 新增用户
     * @param phone
     * @return
     */
    @GetMapping("/addCustomer")
    public ResponseBean<?> addCustomer(String phone) {
        return ResponseBean.success();
    }


    /**
     * 用户修改信息
     * @param updateCustomerRO
     * @return
     */
    @PostMapping("/updateCustomer")
    public ResponseBean<?> updateCustomer(UpdateCustomerRO updateCustomerRO) {
        return ResponseBean.success();
    }




}
