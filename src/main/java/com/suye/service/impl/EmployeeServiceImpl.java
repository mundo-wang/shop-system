package com.suye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suye.entity.Employee;
import com.suye.service.EmployeeService;
import com.suye.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

}




