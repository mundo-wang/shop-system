package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.po.GoodsPO;
import com.ujs.shop.mapper.GoodsMapper;
import com.ujs.shop.service.GoodsService;
import org.springframework.stereotype.Service;

/**
 * @author mundo.wang
 * @date 2023/2/12 18:15
 */


@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsPO> implements GoodsService {
}
