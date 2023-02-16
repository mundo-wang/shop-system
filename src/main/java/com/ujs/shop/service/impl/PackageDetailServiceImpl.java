package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.po.PackageDetailPO;
import com.ujs.shop.mapper.PackageDetailMapper;
import com.ujs.shop.service.PackageDetailService;
import org.springframework.stereotype.Service;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:33
 */

@Service
public class PackageDetailServiceImpl extends ServiceImpl<PackageDetailMapper, PackageDetailPO> implements PackageDetailService {
}
