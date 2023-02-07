package com.ujs.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujs.shop.common.dto.EmployeeInfoDTO;
import com.ujs.shop.common.po.EmployeePO;
import com.ujs.shop.common.ro.AddEmployeeRO;
import com.ujs.shop.common.ro.UpdateEmployeeRO;
import com.ujs.shop.common.ro.UpdatePasswordRO;

/**
 * @author mundo.wang
 * @date 2023/2/6 17:46
 */


public interface EmployeeService extends IService<EmployeePO> {

    void addEmployee(AddEmployeeRO addEmployeeRO);

    void updateEmployee(UpdateEmployeeRO updateEmployeeRO);

    EmployeeInfoDTO getEmployInfo(String id);




    void updatePassword(UpdatePasswordRO updatePasswordRO);
}
