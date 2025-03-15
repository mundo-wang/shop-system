package com.ujs.shop.controller;

import com.ujs.shop.common.base.BaseController;
import com.ujs.shop.common.dto.AddressDTO;
import com.ujs.shop.common.dto.AddressInfoDTO;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.AddAddressRO;
import com.ujs.shop.common.ro.UpdateAddressRO;
import com.ujs.shop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/3/22 1:04
 */


@RestController
@RequestMapping("/shop/address")
public class AddressController extends BaseController {


    @Autowired
    private AddressService addressService;


    /**
     * 新增地址
     *
     * @param addAddressRO
     * @return
     */
    @PostMapping("/addAddress")
    public ResponseBean<?> addAddress(@Valid @RequestBody AddAddressRO addAddressRO) {
        addressService.addAddress(addAddressRO, getCustomerId());
        return ResponseBean.success();
    }


    /**
     * 修改地址
     *
     * @param updateAddressRO
     * @return
     */
    @PostMapping("/updateAddress")
    public ResponseBean<?> updateAddress(@Valid @RequestBody UpdateAddressRO updateAddressRO) {
        addressService.updateAddress(updateAddressRO, getCustomerId());
        return ResponseBean.success();
    }


    /**
     * 地址回显
     *
     * @param id
     * @return
     */
    @GetMapping("/getAddressInfo")
    public ResponseBean<AddressInfoDTO> getAddressInfo(@RequestParam String id) {
        AddressInfoDTO addressInfo = addressService.getAddressInfo(id);
        return ResponseBean.success(addressInfo);
    }


    /**
     * 地址列表
     *
     * @return
     */
    @GetMapping("/addressList")
    public ResponseBean<List<AddressDTO>> addressList() {
        List<AddressDTO> addressDTOS = addressService.addressList(getCustomerId());
        return ResponseBean.success(addressDTOS);
    }


    /**
     * 删除地址
     *
     * @param id
     * @return
     */
    @GetMapping("/removeAddress")
    public ResponseBean<?> removeAddress(@RequestParam String id) {
        addressService.removeAddress(id, getCustomerId());
        return ResponseBean.success();
    }


    /**
     * 设置默认地址
     *
     * @param id
     * @return
     */
    @GetMapping("/setDefault")
    public ResponseBean<?> setDefault(@RequestParam String id) {
        addressService.setDefault(id, getCustomerId());
        return ResponseBean.success();
    }


    /**
     * 获取默认地址
     *
     * @return
     */
    @GetMapping("/getDefault")
    public ResponseBean<AddressInfoDTO> getDefault() {
        AddressInfoDTO addressInfoDTO = addressService.getDefault(getCustomerId());
        return ResponseBean.success(addressInfoDTO);
    }


}
