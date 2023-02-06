package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.po.EmployeePO;
import com.ujs.shop.common.ro.AddEmployeeRO;
import com.ujs.shop.mapper.EmployeeMapper;
import com.ujs.shop.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author mundo.wang
 * @date 2023/2/6 18:57
 */


@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeePO> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void addEmployee(AddEmployeeRO addEmployeeRO) {
        EmployeePO employeePO = new EmployeePO();
        BeanUtils.copyProperties(addEmployeeRO, employeePO);
        employeePO.setId(ConstantBean.getUUIDKey());
        employeePO.setPassword(ConstantBean.INITPASSWORD);
        int result = employeeMapper.insert(employeePO);
    }
}
