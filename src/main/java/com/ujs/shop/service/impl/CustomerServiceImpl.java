package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.po.CustomerPO;
import com.ujs.shop.mapper.CustomerMapper;
import com.ujs.shop.service.CustomerService;
import org.springframework.stereotype.Service;

/**
 * @author mundo.wang
 * @date 2023/2/19 15:28
 */

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, CustomerPO> implements CustomerService {
}
