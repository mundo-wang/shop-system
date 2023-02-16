package com.ujs.shop.controller;

import com.ujs.shop.common.dto.PackageInfoDTO;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.AddPackageRO;
import com.ujs.shop.common.ro.UpdatePackageRO;
import com.ujs.shop.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:34
 *
 * 套餐表对应controller
 */


@RestController
@RequestMapping("/shop/package")
public class PackageController {


    @Autowired
    private PackageService packageService;


    /**
     * 新增套餐
     * @param addPackageRO
     * @return
     */
    @PostMapping("/addPackage")
    public ResponseBean<?> addPackage(@Valid @RequestBody AddPackageRO addPackageRO) {
        packageService.addPackage(addPackageRO);
        return ResponseBean.success();
    }


    /**
     * 修改套餐
     * @param updatePackageRO
     * @return
     */
    @PostMapping("/updatePackage")
    public ResponseBean<?> updatePackage(@Valid @RequestBody UpdatePackageRO updatePackageRO) {
        packageService.updatePackage(updatePackageRO);
        return ResponseBean.success();
    }


    /**
     * 回显套餐具体信息
     * @param id
     * @return
     */
    @GetMapping("/getPackageInfo")
    public ResponseBean<PackageInfoDTO> getPackageInfo(String id) {
        PackageInfoDTO packageInfo = packageService.getPackageInfo(id);
        return ResponseBean.success(packageInfo);
    }



}
