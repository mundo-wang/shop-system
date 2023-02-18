package com.ujs.shop.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.EmployeeInfoDTO;
import com.ujs.shop.common.dto.EmployeePageDTO;
import com.ujs.shop.common.dto.UserInfoDTO;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.global.PageFormBean;
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
import java.util.ArrayList;
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
    public void addEmployee(AddEmployeeRO addEmployeeRO, String userName) {
//        非管理员不可添加用户
        if (! ConstantBean.SUPER_USER.equals(userName)) {
            throw new ServiceException(ResponseCodeEnum.NO_PERMISSION);
        }

//        员工用户名重复提醒
        if (EmployeeNames().contains(addEmployeeRO.getUserName())) {
            throw new ServiceException(ResponseCodeEnum.USER_NAME_ERROR);
        }

        EmployeePO employeePO = new EmployeePO();
        BeanUtils.copyProperties(addEmployeeRO, employeePO);
        employeePO.setId(ConstantBean.getUUID());
        employeePO.setPassword(ConstantBean.INITPASSWORD);
        employeeMapper.insert(employeePO);
        log.info("添加员工成功");
    }

    @Override
    public void updateEmployee(UpdateEmployeeRO updateEmployeeRO, String userName) {
        EmployeePO employeePO = employeeMapper.selectById(updateEmployeeRO.getId());
        if (employeePO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_USER);
        }
//        非管理员或自己不得更改用户信息
        if (! (employeePO.getUserName().equals(userName) || ConstantBean.SUPER_USER.equals(userName))) {
            throw new ServiceException(ResponseCodeEnum.NO_PERMISSION);
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
    public EmployeeInfoDTO getEmployInfo(String id, String userName) {
        EmployeePO employeePO = employeeMapper.selectById(id);
        if (employeePO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_USER);
        }
        if (! (employeePO.getUserName().equals(userName) || ConstantBean.SUPER_USER.equals(userName))) {
            throw new ServiceException(ResponseCodeEnum.NO_PERMISSION);
        }
        EmployeeInfoDTO employeeInfoDTO = new EmployeeInfoDTO();
        BeanUtils.copyProperties(employeePO, employeeInfoDTO);
        return employeeInfoDTO;
    }


    /**
     * 这里采取的步骤：验证账号密码通过后，以用户名+id前八位生成一个固定token，以token为key，userInfoDTO为value存入Redis，有效期设为24小时。
     * 以上面生成的token和一个随机UUID作为JWT的payload部分，生成一个JWT返回给前端，前端下次请求就将 Authorization=JWT 放到请求头中。
     * 在过滤器拦截请求时，解析JWT，获取token，再从Redis获取userInfoDTO，如果过期了，需要重新登录。
     * @param employeeLoginRO
     * @return
     */
    @Override
    public String login(EmployeeLoginRO employeeLoginRO) {
        LambdaQueryWrapper<EmployeePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmployeePO::getUserName, employeeLoginRO.getUserName());
        List<EmployeePO> employeePOS = employeeMapper.selectList(wrapper);
        if (employeePOS.isEmpty()) {
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

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(employeePO, userInfoDTO);
        String token = userInfoDTO.getUserName() + "_" + userInfoDTO.getId().substring(0, 8);
        if (! redisTemplate.hasKey(token)) {
            redisTemplate.boundValueOps(token).set(userInfoDTO, 24, TimeUnit.HOURS);
        }
        return JWTHelper.getJWTToken(token, ConstantBean.SECRET);
    }

    @Override
    public void logout(String token) {
        if (redisTemplate.hasKey(token)) {
            redisTemplate.delete(token);
        }
    }

    /**
     * 此处逻辑：如果是普通用户，是无法查看管理员信息的，所以查询结果要去掉管理员的数据。
     * 如果是管理员，要求管理员的信息永远处在第一条，但是我们是按照创建时间倒序排序，所以我们需要把管理员的数据塞到List集合第一个。
     *
     * @param page
     * @param size
     * @param userName
     * @param loginName
     * @return
     */
    @Override
    public PageFormBean<EmployeePageDTO> employeePage(Integer page, Integer size, String userName, String loginName) {
        Page<EmployeePageDTO> page1 = new Page<>(page, size);
        IPage<EmployeePageDTO> page2 = employeeMapper.employeePage(page1, userName);
        List<EmployeePageDTO> records = page2.getRecords();
        EmployeePageDTO employeePageDTO = records.get(records.size() - 1);
        records.remove(records.size() - 1);
        page2.setTotal(page2.getTotal() - 1);
        if (ConstantBean.SUPER_USER.equals(loginName)) {
            List<EmployeePageDTO> temp = new ArrayList<>();
            temp.add(employeePageDTO);
            temp.addAll(records);
            records = temp;
            page2.setTotal(page2.getTotal() + 1);
        }
        page2.setRecords(records);
        return new PageFormBean<>(page2);
    }

    @Override
    public void changeStatus(String id, Boolean status, String userName) {
        EmployeePO employeePO = employeeMapper.selectById(id);
        if (employeePO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_USER);
        }
        if (! ConstantBean.SUPER_USER.equals(userName)) {
            throw new ServiceException(ResponseCodeEnum.NO_PERMISSION);
        }
        if (employeePO.getUserName().equals(ConstantBean.SUPER_USER)) {
            throw new ServiceException(ResponseCodeEnum.DISABLE_ERROR);
        }
        employeePO.setStatus(status);
        employeeMapper.updateById(employeePO);
        log.info("修改员工状态成功");
    }

    @Override
    public void updatePassword(UpdatePasswordRO updatePasswordRO, String userName) {
        EmployeePO employeePO = employeeMapper.selectById(updatePasswordRO.getId());
        if (employeePO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_USER);
        }
//        密码只有自己能改，管理员也改不了
        if (! employeePO.getUserName().equals(userName)) {
            throw new ServiceException(ResponseCodeEnum.NO_PERMISSION);
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

    @Override
    public void removeEmployee(String id, String userName) {
        EmployeePO employeePO = employeeMapper.selectById(id);
        if (employeePO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_USER);
        }
        if (! ConstantBean.SUPER_USER.equals(userName)) {
            throw new ServiceException(ResponseCodeEnum.NO_PERMISSION);
        }
        if (! employeePO.getStatus()) {
            throw new ServiceException(ResponseCodeEnum.STATUS_ERROR);
        }
        employeeMapper.deleteById(id);
        log.info("删除用户成功");
    }
}
