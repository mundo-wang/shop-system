package com.ujs.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujs.shop.common.dto.PackageInfoDTO;
import com.ujs.shop.common.po.PackagePO;
import com.ujs.shop.common.ro.AddPackageRO;
import com.ujs.shop.common.ro.ChangePackageStatusRO;
import com.ujs.shop.common.ro.UpdatePackageRO;

import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:28
 */
public interface PackageService extends IService<PackagePO> {


    void addPackage(AddPackageRO addPackageRO);

    void updatePackage(UpdatePackageRO updatePackageRO);

    PackageInfoDTO getPackageInfo(String id);

    void removePackage(List<String> packageIds);

    void changeStatus(List<String> packageIds, Boolean status);


}
