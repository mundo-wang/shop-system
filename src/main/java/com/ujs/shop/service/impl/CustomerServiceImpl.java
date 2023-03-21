package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.po.CustomerPO;
import com.ujs.shop.common.ro.UpdateCustomerRO;
import com.ujs.shop.mapper.CustomerMapper;
import com.ujs.shop.service.CustomerService;
import com.ujs.shop.utils.ValidateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author mundo.wang
 * @date 2023/2/19 15:28
 */

@Service
@Slf4j
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, CustomerPO> implements CustomerService {


    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取所有电话号集合
     * @return
     */
    private Set<String> phoneSet() {
        List<CustomerPO> customerPOS = customerMapper.selectList(null);
        return customerPOS.stream().map(CustomerPO::getPhone).collect(Collectors.toSet());
    }

    /**
     * 获取所有用户名集合
     * @return
     */
    private Set<String> userNameSet() {
        List<CustomerPO> customerPOS = customerMapper.selectList(null);
        return customerPOS.stream().map(CustomerPO::getUserName).collect(Collectors.toSet());
    }

    @Override
    public void getVerifyCode(String phone) {
        String verifyCode = ValidateCodeUtil.generateValidateCode(6).toString();
        log.info("手机号：" + phone + "，获取到验证码：" + verifyCode);
        redisTemplate.boundValueOps(ConstantBean.VERIFY_PREFIX + phone).set(verifyCode, 2, TimeUnit.MINUTES);
    }

    @Override
    public String login(String phone, String verifyCode) {
        String code = (String) redisTemplate.boundValueOps(ConstantBean.VERIFY_PREFIX + phone).get();
        if (code == null || code.equals("")) {
            throw new ServiceException(ResponseCodeEnum.VERIFY_OVERDUE);
        }
        if (! verifyCode.equals(code)) {
            throw new ServiceException(ResponseCodeEnum.VERIFY_ERROR);
        }
        redisTemplate.delete(phone);
        String token = ConstantBean.USER_PREFIX + phone;
        if (! redisTemplate.hasKey(token)) {
            redisTemplate.boundValueOps(token).set(phone);
        }

        CustomerPO customerPO = new CustomerPO();
        if (phoneSet().contains(phone)) {
            return token;
        }
        customerPO.setId(ConstantBean.getUUID());
        customerPO.setPhone(phone);
        String userName = ConstantBean.USERNAME_PREFIX + ConstantBean.getUUID().substring(0, 8);
        while (userNameSet().contains(userName)) {
            userName = ConstantBean.USERNAME_PREFIX + ConstantBean.getUUID().substring(0, 8);
        }
        customerPO.setUserName(userName);
        customerPO.setCreateTime(LocalDateTime.now());
        customerPO.setUpdateTime(LocalDateTime.now());
        customerMapper.insert(customerPO);
        return token;
    }

    @Override
    public void updateCustomer(UpdateCustomerRO updateCustomerRO) {
        CustomerPO customerPO = customerMapper.selectById(updateCustomerRO.getId());
        if (customerPO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_USER);
        }
        if (userNameSet().contains(updateCustomerRO.getUserName())) {
            throw new ServiceException(ResponseCodeEnum.USERNAME_UNIQUE);
        }
        BeanUtils.copyProperties(updateCustomerRO, customerPO);
        customerMapper.updateById(customerPO);
    }
}
