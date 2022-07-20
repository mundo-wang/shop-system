package com.suye.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.suye.common.R;
import com.suye.entity.User;
import com.suye.service.UserService;
import com.suye.utils.SMSUtils;
import com.suye.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author sj.w
 */

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {

//        获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
//            生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code = {}", code);

//            调用阿里云提供的短信服务api
//            SMSUtils.sendMessage("蒙多外卖", "", phone, code);

//            保存生成的验证码，存到session中（与用户输入的验证码做校验）
//            session.setAttribute(phone, code);

//            将生成的验证码缓存在redis中，有效期5分钟
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

            return R.success("手机验证码发送成功");
        }
        return R.error("短信发送失败");
    }


    /**
     * 移动端用户登录
     * @param map  key：手机号， value：验证码
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());

//        获取手机号
        String phone = map.get("phone").toString();

//        获取验证码
        String code = map.get("code").toString();

//        从session中获取保存的验证码
//        Object codeInSession = session.getAttribute(phone);

//        从redis中获取缓存的验证码
        String codeInRedis = redisTemplate.opsForValue().get(phone);

//        进行验证码比对（页面提交的和session中保存的比对）
        if (codeInRedis != null && code.equals(code)) {
//          判断当前手机号对应的用户是否为新用户，若是，自动完成注册
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }

//            通过过滤器的判断
            session.setAttribute("user", user.getId());

//            如果用户登录成功，删除redis中缓存的验证码
            redisTemplate.delete(phone);
            return R.success(user);

        }
        return R.error("登录失败");
    }
}
