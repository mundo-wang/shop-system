package com.ujs.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujs.shop.common.po.AddressPO;
import com.ujs.shop.common.ro.AddAddressRO;
import com.ujs.shop.common.ro.UpdateAddressRO;

/**
 * @author mundo.wang
 * @date 2023/3/22 1:02
 */
public interface AddressService extends IService<AddressPO> {

    void addAddress(AddAddressRO addAddressRO, String id);

    void updateAddress(UpdateAddressRO updateAddressRO, String id);
}
