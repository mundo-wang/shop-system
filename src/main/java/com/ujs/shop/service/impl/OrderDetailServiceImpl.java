package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.po.OrderDetailPO;
import com.ujs.shop.mapper.OrderDetailMapper;
import com.ujs.shop.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author mundo.wang
 * @date 2023/3/27 13:39
 */

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetailPO> implements OrderDetailService {
}
