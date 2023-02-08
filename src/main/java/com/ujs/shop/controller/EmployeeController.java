package com.ujs.shop.controller;


import com.ujs.shop.common.dto.EmployeeInfoDTO;
import com.ujs.shop.common.dto.EmployeePageDTO;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.po.EmployeePO;
import com.ujs.shop.common.ro.*;
import com.ujs.shop.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * @author mundo.wang
 * @date 2023/2/6 18:59
 *
 * 员工表对应controller
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
        employeeService.addEmployee(addEmployeeRO);
        return ResponseBean.success();
    }


    /**
     * 修改员工信息
     * @param updateEmployeeRO
     * @return
     */
    @PostMapping("/updateEmployee")
    public ResponseBean<?> updateEmployee(@Valid @RequestBody UpdateEmployeeRO updateEmployeeRO) {
        employeeService.updateEmployee(updateEmployeeRO);
        return ResponseBean.success();
    }


    /**
     * 员工信息回显
     * @param id
     * @return
     */
    @GetMapping("/getEmployInfo")
    public ResponseBean<EmployeeInfoDTO> getEmployInfo(String id) {
        EmployeeInfoDTO employeeInfoDTO = employeeService.getEmployInfo(id);
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
//        从请求头中获取jwtToken，为的是在登出的时候把Redis中相应的数据移除
        employeeService.logout(request.getHeader("Authorization"));
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
        employeeService.updatePassword(updatePasswordRO);
        return ResponseBean.success();
    }



}
