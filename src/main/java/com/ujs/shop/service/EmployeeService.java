package com.ujs.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujs.shop.common.dto.EmployeeInfoDTO;
import com.ujs.shop.common.dto.EmployeePageDTO;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.po.EmployeePO;
import com.ujs.shop.common.ro.AddEmployeeRO;
import com.ujs.shop.common.ro.EmployeeLoginRO;
import com.ujs.shop.common.ro.UpdateEmployeeRO;
import com.ujs.shop.common.ro.UpdatePasswordRO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mundo.wang
 * @date 2023/2/6 17:46
 */


public interface EmployeeService extends IService<EmployeePO> {

    void addEmployee(AddEmployeeRO addEmployeeRO, String userName);

    void updateEmployee(UpdateEmployeeRO updateEmployeeRO, String userName);

    EmployeeInfoDTO getEmployInfo(String id, String userName);

    String login(EmployeeLoginRO employeeLoginRO);

    void logout(String token);

    PageFormBean<EmployeePageDTO> employeePage(Integer page, Integer size, String userName, String loginName);


    void changeStatus(String id, Boolean status, String userName);

    void updatePassword(UpdatePasswordRO updatePasswordRO, String userName);

    void removeEmployee(String id, String userName);
}
