package com.ujs.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujs.shop.common.po.EmployeePO;
import com.ujs.shop.common.ro.AddEmployeeRO;

/**
 * @author mundo.wang
 * @date 2023/2/6 17:46
 */


public interface EmployeeService extends IService<EmployeePO> {

    void addEmployee(AddEmployeeRO addEmployeeRO);
}
