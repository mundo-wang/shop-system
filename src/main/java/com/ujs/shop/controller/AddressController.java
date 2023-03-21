package com.ujs.shop.controller;

import com.ujs.shop.common.base.BaseController;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.AddAddressRO;
import com.ujs.shop.common.ro.UpdateAddressRO;
import com.ujs.shop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author mundo.wang
 * @date 2023/3/22 1:04
 */


@RestController
@RequestMapping("/shop/address")
public class AddressController extends BaseController {


    @Autowired
    private AddressService addressService;


    @PostMapping("/addAddress")
    public ResponseBean<?> addAddress(@Valid @RequestBody AddAddressRO addAddressRO) {
        addressService.addAddress(addAddressRO, getCustomerId());
        return ResponseBean.success();
    }


    @PostMapping("/updateAddress")
    public ResponseBean<?> updateAddress(@Valid @RequestBody UpdateAddressRO updateAddressRO) {
        addressService.updateAddress(updateAddressRO, getCustomerId());
        return ResponseBean.success();
    }
}
