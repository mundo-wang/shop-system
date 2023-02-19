package com.ujs.shop.controller;

import com.ujs.shop.common.dto.PackageInfoDTO;
import com.ujs.shop.common.dto.PackagePageDTO;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.AddPackageRO;
import com.ujs.shop.common.ro.ChangePackageStatusRO;
import com.ujs.shop.common.ro.PackagePageRO;
import com.ujs.shop.common.ro.UpdatePackageRO;
import com.ujs.shop.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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


    /**
     * 单体/批量删除套餐及其对应商品
     * @param packageIds
     * @return
     */
    @GetMapping("/removePackage")
    public ResponseBean<?> removePackage(@RequestParam("packageIds") List<String> packageIds) {
        packageService.removePackage(packageIds);
        return ResponseBean.success();
    }


    /**
     * 单体/批量 启用/禁用
     * @param ro
     * @return
     */
    @PostMapping("/changeStatus")
    public ResponseBean<?> changeStatus(@Valid @RequestBody ChangePackageStatusRO ro) {
        packageService.changeStatus(ro.getPackageIds(), ro.getStatus());
        return ResponseBean.success();
    }


    /**
     * 套餐分页展示
     * @param packagePageRO
     * @return
     */
    @PostMapping("/packagePage")
    public ResponseBean<PageFormBean<PackagePageDTO>> packagePage(@Valid @RequestBody PackagePageRO packagePageRO) {
        PageFormBean<PackagePageDTO> packagePage = packageService.packagePage(
                packagePageRO.getPage(),
                packagePageRO.getSize(),
                packagePageRO.getName(),
                packagePageRO.getCategoryId()
        );
        return ResponseBean.success(packagePage);
    }

}
