package com.suye.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suye.common.R;
import com.suye.entity.Employee;
import com.suye.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Struct;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author sj.w
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request  登录成功后将员工id存入session
     * @param employee  使用@RequestBody注解接收前端传给后端的请求体中的json格式数据
     * @return
     */

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

//        1.将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(wrapper);

//        3、如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败");
        }

//        4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }

//        5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

//        6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);

    }


    /**
     * 员工退出登录
     * @param request  操作session
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
//        清理session保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }


    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());

//        设置初始密码123456，需要MD5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        Date date = new Date();
//        employee.setCreateTime(date);
//        employee.setUpdateTime(date);

//        获得当前登录用户的id
//        Long empId = (Long) request.getSession().getAttribute("employee");

//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

//        mybatis-plus提供的save方法
        employeeService.save(employee);
        return R.success("新增员工成功");
    }


    /**
     * 员工信息的分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);

//        构造分页构造器
        Page pageInfo = new Page(page, pageSize);

//        构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
//        添加过滤条件（判断name不是null，不是空字符串）
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
//        添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

//        执行查询
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }


    /**
     * 根据id修改员工信息
     * @param employee
     * @return
     */

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为：" + id);

//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateTime(new Date());
//        employee.setUpdateUser(empId);
//        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }


    /**
     * 根据id查询员工信息（当我们点击编辑时，把原数据回显到页面上）
     * @param id
     * @return
     */
    @GetMapping("/{id}")   // rest
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }
}
