package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.AddressDTO;
import com.ujs.shop.common.dto.AddressInfoDTO;
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
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public AddressInfoDTO getAddressInfo(String id) {
        AddressPO addressPO = addressMapper.selectById(id);
        if (addressPO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_ADDRESS);
        }
        AddressInfoDTO addressInfoDTO = new AddressInfoDTO();
        BeanUtils.copyProperties(addressPO, addressInfoDTO);
        return addressInfoDTO;
    }

    @Override
    public List<AddressDTO> addressList(String userId) {
        LambdaQueryWrapper<AddressPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressPO::getUserId, userId)
                .orderByDesc(AddressPO::getCreateTime);
        List<AddressPO> addressPOS = addressMapper.selectList(wrapper);
        List<AddressDTO> addressDTOS = new ArrayList<>();
        for (AddressPO addressPO : addressPOS) {
            AddressDTO addressDTO = new AddressDTO();
            BeanUtils.copyProperties(addressPO, addressDTO);
            addressDTOS.add(addressDTO);
        }
        return addressDTOS;
    }

    @Override
    public void removeAddress(String id, String customerId) {
        AddressPO addressPO = addressMapper.selectById(id);
        if (addressPO == null || !addressPO.getUserId().equals(customerId)) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_ADDRESS);
        }
        if (addressPO.getIsDefault()) {
            throw new ServiceException(ResponseCodeEnum.DEFAULT_ADDRESS);
        }
        addressMapper.deleteById(id);
    }

    @Override
    public void setDefault(String id, String customerId) {
        AddressPO addressPO = addressMapper.selectById(id);
        if (addressPO == null || !addressPO.getUserId().equals(customerId)) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_ADDRESS);
        }
        AddressPO addressPO1 = addressMapper.selectOne(new LambdaQueryWrapper<AddressPO>()
                .eq(AddressPO::getIsDefault, true)
                .eq(AddressPO::getUserId, customerId));
        if (addressPO1 != null) {
            addressPO1.setIsDefault(false);
            addressMapper.updateById(addressPO1);
        }
        addressPO.setIsDefault(true);
        addressMapper.updateById(addressPO);
    }

    @Override
    public AddressInfoDTO getDefault(String customerId) {
        AddressPO addressPO = addressMapper.selectOne(new LambdaQueryWrapper<AddressPO>()
                .eq(AddressPO::getIsDefault, true)
                .eq(AddressPO::getUserId, customerId));
        AddressInfoDTO addressInfoDTO = new AddressInfoDTO();
        BeanUtils.copyProperties(addressPO, addressInfoDTO);
        return addressInfoDTO;
    }
}
