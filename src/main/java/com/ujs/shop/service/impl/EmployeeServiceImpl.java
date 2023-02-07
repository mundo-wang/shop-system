package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.EmployeeInfoDTO;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.po.EmployeePO;
import com.ujs.shop.common.ro.AddEmployeeRO;
import com.ujs.shop.common.ro.UpdateEmployeeRO;
import com.ujs.shop.common.ro.UpdatePasswordRO;
import com.ujs.shop.mapper.EmployeeMapper;
import com.ujs.shop.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author mundo.wang
 * @date 2023/2/6 18:57
 */


@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeePO> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 获取所有员工的用户名集合
     * @return
     */
    public Set<String> EmployeeNames() {
        List<EmployeePO> employeePOS = employeeMapper.selectList(null);
        return employeePOS.stream().map(EmployeePO::getUserName).collect(Collectors.toSet());
    }

    @Override
    public void addEmployee(AddEmployeeRO addEmployeeRO) {
//        员工用户名重复提醒
        if (EmployeeNames().contains(addEmployeeRO.getUserName())) {
            throw new ServiceException(ResponseCodeEnum.USER_NAME_ERROR);
        }

        EmployeePO employeePO = new EmployeePO();
        BeanUtils.copyProperties(addEmployeeRO, employeePO);
        employeePO.setId(ConstantBean.getUUIDKey());
        employeePO.setPassword(ConstantBean.INITPASSWORD);
        employeeMapper.insert(employeePO);
        log.info("添加员工成功");
    }

    @Override
    public void updateEmployee(UpdateEmployeeRO updateEmployeeRO) {
        EmployeePO employeePO = employeeMapper.selectById(updateEmployeeRO.getId());
        if (employeePO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_USER);
        }
        if (!employeePO.getUserName().equals(updateEmployeeRO.getUserName())) {
            if (EmployeeNames().contains(updateEmployeeRO.getUserName())) {
                throw new ServiceException(ResponseCodeEnum.USER_NAME_ERROR);
            }
        }
        BeanUtils.copyProperties(updateEmployeeRO, employeePO);
        employeeMapper.updateById(employeePO);
        log.info("修改员工信息成功");
    }

    @Override
    public EmployeeInfoDTO getEmployInfo(String id) {
        EmployeePO employeePO = employeeMapper.selectById(id);
        if (employeePO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_USER);
        }
        EmployeeInfoDTO employeeInfoDTO = new EmployeeInfoDTO();
        BeanUtils.copyProperties(employeePO, employeeInfoDTO);
        return employeeInfoDTO;
    }

    @Override
    public void updatePassword(UpdatePasswordRO updatePasswordRO) {
        EmployeePO employeePO = employeeMapper.selectById(updatePasswordRO.getId());
        if (employeePO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_USER);
        }
        String oldPassMD5 = DigestUtils.md5DigestAsHex(updatePasswordRO.getOldPassword().getBytes());
        String newPassMD5 = DigestUtils.md5DigestAsHex(updatePasswordRO.getNewPassword().getBytes());
        if (! employeePO.getPassword().equals(oldPassMD5)) {
            throw new ServiceException(ResponseCodeEnum.WRONG_PASSWORD);
        } else if (oldPassMD5.equals(newPassMD5)) {
            throw new ServiceException(ResponseCodeEnum.SAME_PASSWORD);
        }
        employeePO.setPassword(newPassMD5);
        employeeMapper.updateById(employeePO);
        log.info("修改密码成功");
    }
}
