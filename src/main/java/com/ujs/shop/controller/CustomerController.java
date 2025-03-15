package com.ujs.shop.controller;

import com.ujs.shop.common.base.BaseController;
import com.ujs.shop.common.dto.CustomerInfoDTO;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.UpdateCustomerRO;
import com.ujs.shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mundo.wang
 * @date 2023/2/19 15:29
 */


@RestController
@RequestMapping("/shop/customer")
public class CustomerController extends BaseController {


    @Autowired
    private CustomerService customerService;


    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    @GetMapping("/getVerifyCode")
    public ResponseBean<?> getVerifyCode(@RequestParam String phone) {
        customerService.getVerifyCode(phone);
        return ResponseBean.success();
    }


    /**
     * 用户登录
     *
     * @param phone
     * @return
     */
    @GetMapping("/login")
    public ResponseBean<String> login(@RequestParam String phone, @RequestParam String verifyCode) {
        String token = customerService.login(phone, verifyCode);
        return ResponseBean.success(token);
    }


    /**
     * 用户修改信息
     *
     * @param updateCustomerRO
     * @return
     */
    @PostMapping("/updateCustomer")
    public ResponseBean<?> updateCustomer(@RequestBody @Valid UpdateCustomerRO updateCustomerRO) {
        customerService.updateCustomer(updateCustomerRO);
        return ResponseBean.success();
    }


    /**
     * 用户信息回显
     *
     * @param id
     * @return
     */
    @GetMapping("/getCustomerInfo")
    public ResponseBean<CustomerInfoDTO> getCustomerInfo(@RequestParam String id) {
        CustomerInfoDTO customerInfo = customerService.getCustomerInfo(id);
        return ResponseBean.success(customerInfo);
    }


    /**
     * 用户登出
     *
     * @return
     */
    @GetMapping("/logout")
    public ResponseBean<?> logout() {
        String token = request.getHeader(ConstantBean.TOKEN);
        customerService.logout(token);
        return ResponseBean.success();
    }


}
