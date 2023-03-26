package com.ujs.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujs.shop.common.dto.CustomerInfoDTO;
import com.ujs.shop.common.po.CustomerPO;
import com.ujs.shop.common.ro.UpdateCustomerRO;

/**
 * @author mundo.wang
 * @date 2023/2/19 15:27
 */
public interface CustomerService extends IService<CustomerPO> {

    void getVerifyCode(String phone);

    String login(String phone, String verifyCode);

    void updateCustomer(UpdateCustomerRO updateCustomerRO);

    CustomerInfoDTO getCustomerInfo(String id);

    void logout(String token);
}
