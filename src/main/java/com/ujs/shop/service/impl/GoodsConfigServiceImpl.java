package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.po.GoodsConfigPO;
import com.ujs.shop.mapper.GoodsConfigMapper;
import com.ujs.shop.service.GoodsConfigService;
import org.springframework.stereotype.Service;

/**
 * @author mundo.wang
 * @date 2023/2/12 18:13
 */


@Service
public class GoodsConfigServiceImpl extends ServiceImpl<GoodsConfigMapper, GoodsConfigPO> implements GoodsConfigService {
}
