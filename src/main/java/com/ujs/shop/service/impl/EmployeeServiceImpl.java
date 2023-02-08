package com.ujs.shop.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.EmployeeInfoDTO;
import com.ujs.shop.common.dto.UserInfoDTO;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.po.EmployeePO;
import com.ujs.shop.common.ro.AddEmployeeRO;
import com.ujs.shop.common.ro.EmployeeLoginRO;
import com.ujs.shop.common.ro.UpdateEmployeeRO;
import com.ujs.shop.common.ro.UpdatePasswordRO;
import com.ujs.shop.mapper.EmployeeMapper;
import com.ujs.shop.service.EmployeeService;
import com.ujs.shop.utils.JWTHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
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

    @Autowired
    private RedisTemplate redisTemplate;


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
        if (! employeePO.getUserName().equals(updateEmployeeRO.getUserName())) {
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


    /**
     * 这里采取的步骤：验证账号密码通过后，生成一个随机token，以token为key，userInfoDTO为value存入Redis，有效期设为4小时。
     * 以随机生成的token作为JWT的payload部分，生成一个JWT返回给前端，前端下次请求就将 Authorization=JWT 放到请求头中。
     * 下次请求发送过来时，解析JWT，获取token，再从Redis获取userInfoDTO，如果过期了，需要重新登录。
     * @param employeeLoginRO
     * @return
     */
    @Override
    public String login(EmployeeLoginRO employeeLoginRO) {
        LambdaQueryWrapper<EmployeePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmployeePO::getUserName, employeeLoginRO.getUserName());
        List<EmployeePO> employeePOS = employeeMapper.selectList(wrapper);
        if (employeePOS.size() == 0) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_USER);
        }
        EmployeePO employeePO = employeePOS.get(0);
        if (employeePO.getStatus()) {
            throw new ServiceException(ResponseCodeEnum.USER_DISABLE);
        }
        String loginPassword = DigestUtils.md5DigestAsHex(employeeLoginRO.getPassword().getBytes());
        if (! loginPassword.equals(employeePO.getPassword())) {
            throw new ServiceException(ResponseCodeEnum.PASSWORD_WRONG);
        }

        String token = ConstantBean.getUUIDKey();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(employeePO, userInfoDTO);
        redisTemplate.boundValueOps(token).set(userInfoDTO, 4, TimeUnit.HOURS);
        return JWTHelper.getJWTToken(token, ConstantBean.SECRET);
    }

    @Override
    public void logout(String jwtToken) {
        DecodedJWT decode = JWTHelper.decode(jwtToken);
        if (! jwtToken.equals(decode.getToken())) {
            throw new ServiceException(ResponseCodeEnum.JWT_TOKEN_ERROR);
        }
        String token = JWTHelper.getToken(jwtToken);
        if (redisTemplate.hasKey(token)) {
            redisTemplate.delete(token);
        }
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
