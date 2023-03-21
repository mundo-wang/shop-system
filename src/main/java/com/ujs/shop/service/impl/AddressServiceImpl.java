package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.po.AddressPO;
import com.ujs.shop.common.ro.AddAddressRO;
import com.ujs.shop.common.ro.UpdateAddressRO;
import com.ujs.shop.mapper.AddressMapper;
import com.ujs.shop.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author mundo.wang
 * @date 2023/3/22 1:02
 */

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressPO> implements AddressService {


    @Autowired
    private AddressMapper addressMapper;

    @Override
    public void addAddress(AddAddressRO addAddressRO, String id) {
        AddressPO addressPO = new AddressPO();
        addressPO.setUserId(id);
        BeanUtils.copyProperties(addAddressRO, addressPO);
        addressPO.setId(ConstantBean.getUUID());
        addressPO.setCreateTime(LocalDateTime.now());
        addressPO.setUpdateTime(LocalDateTime.now());
        addressMapper.insert(addressPO);
    }

    @Override
    public void updateAddress(UpdateAddressRO updateAddressRO, String id) {
        AddressPO addressPO = addressMapper.selectById(updateAddressRO.getId());
        if (addressPO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_ADDRESS);
        }
        BeanUtils.copyProperties(updateAddressRO, addressPO);
        addressPO.setUserId(id);
        addressPO.setUpdateTime(LocalDateTime.now());
        addressMapper.updateById(addressPO);
    }
}
