package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.PackageInfoDTO;
import com.ujs.shop.common.po.PackagePO;
import com.ujs.shop.common.ro.AddPackageRO;
import com.ujs.shop.common.ro.UpdatePackageRO;
import com.ujs.shop.mapper.PackageMapper;
import com.ujs.shop.service.PackageService;
import org.springframework.stereotype.Service;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:32
 */


@Service
public class PackageServiceImpl extends ServiceImpl<PackageMapper, PackagePO> implements PackageService {
    @Override
    public void addPackage(AddPackageRO addPackageRO) {

    }

    @Override
    public void updatePackage(UpdatePackageRO updatePackageRO) {

    }

    @Override
    public PackageInfoDTO getPackageInfo(String id) {
        return null;
    }
}
