package com.ujs.shop.controller;


import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.common.ro.AddEmployeeRO;
import com.ujs.shop.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mundo.wang
 * @date 2023/2/6 18:59
 *
 * 员工表对应controller
 */


@RestController
@RequestMapping("/shop/employee")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    @PostMapping("addEmployee")
    public ResponseBean<?> addEmployee(@RequestBody AddEmployeeRO addEmployeeRO) {
        employeeService.addEmployee(addEmployeeRO);
        return ResponseBean.success();
    }

}
