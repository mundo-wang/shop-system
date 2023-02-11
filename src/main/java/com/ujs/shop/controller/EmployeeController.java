package com.ujs.shop.controller;


import com.ujs.shop.common.base.BaseController;
import com.ujs.shop.common.dto.EmployeeInfoDTO;
import com.ujs.shop.common.dto.EmployeePageDTO;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.*;
import com.ujs.shop.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mundo.wang
 * @date 2023/2/6 18:59
 *
 * shop_employee表对应controller
 */


@RestController
@RequestMapping("/shop/employee")
public class EmployeeController extends BaseController {


    @Autowired
    private EmployeeService employeeService;



    /**
     * 增加员工
     * @param addEmployeeRO
     * @return
     */
    @PostMapping("/addEmployee")
    public ResponseBean<?> addEmployee(@Valid @RequestBody AddEmployeeRO addEmployeeRO) {
        employeeService.addEmployee(addEmployeeRO, getUserName());
        return ResponseBean.success();
    }


    /**
     * 员工修改信息
     * @param updateEmployeeRO
     * @return
     */
    @PostMapping("/updateEmployee")
    public ResponseBean<?> updateEmployee(@Valid @RequestBody UpdateEmployeeRO updateEmployeeRO) {
        employeeService.updateEmployee(updateEmployeeRO, getUserName());
        return ResponseBean.success();
    }


    /**
     * 员工信息回显
     * @param id
     * @return
     */
    @GetMapping("/getEmployInfo")
    public ResponseBean<EmployeeInfoDTO> getEmployInfo(String id) {
        EmployeeInfoDTO employeeInfoDTO = employeeService.getEmployInfo(id, getUserName());
        return ResponseBean.success(employeeInfoDTO);
    }


    /**
     * 员工登录平台
     * @param employeeLoginRO
     * @return jwt字符串
     */
    @PostMapping("/login")
    public ResponseBean<String> login(@Valid @RequestBody EmployeeLoginRO employeeLoginRO) {
        String jwtToken = employeeService.login(employeeLoginRO);
        return ResponseBean.success(jwtToken);
    }


    /**
     * 员工登出平台
     * @return
     */
    @GetMapping("/logout")
    public ResponseBean<?> logout() {
//        从请求头中获取jwtToken转为token，为的是在登出的时候把Redis中相应的数据移除
        employeeService.logout(getToken());
        return ResponseBean.success();
    }


    /**
     * 员工信息分页展示
     * @param employeePageRO
     * @return
     */
    @PostMapping("/employeePage")
    public ResponseBean<EmployeePageDTO> employeePage(@Valid @RequestBody EmployeePageRO employeePageRO) {
        return null;
    }


    /**
     * 员工进行密码修改
     * @param updatePasswordRO
     * @return
     */
    @PostMapping("/updatePassword")
    public ResponseBean<?> updatePassword(@Valid @RequestBody UpdatePasswordRO updatePasswordRO) {
        employeeService.updatePassword(updatePasswordRO, getUserName());
        return ResponseBean.success();
    }


    /**
     * 管理员更改员工状态
     * @param status
     * @return
     */
    @GetMapping("/changeStatus")
    public ResponseBean<?> changeStatus(String id, Boolean status) {
        employeeService.changeStatus(id, status, getUserName());
        return ResponseBean.success();
    }


    /**
     * 管理员移除员工
     * @param id
     * @return
     */
    @GetMapping("/removeEmployee")
    public ResponseBean<?> removeEmployee(String id) {
        employeeService.removeEmployee(id, getUserName());
        return ResponseBean.success();
    }



}
