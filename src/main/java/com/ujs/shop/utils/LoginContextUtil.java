package com.ujs.shop.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ControllerException;
import com.ujs.shop.common.global.ConstantBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author mundo.wang
 * @date 2023/2/11 17:16
 *
 *
 * 当前登录用户类，用于在数据库字段填充时拿到当前用户名
 */


@Component
@Slf4j
public class LoginContextUtil implements EnvironmentAware {

    private static Environment staticEnvironment;

    @Override
    public void setEnvironment(Environment environment) {
        staticEnvironment = environment;
    }


    public static String getCurrentUserName() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes()).getRequest();
            if (Objects.isNull(request)) {
                return null;
            }
            String header = request.getHeader(ConstantBean.AUTHORIZATION);
            if (StringUtils.isBlank(header)) {
                return null;
            }
            String userName = JWTHelper.getToken(header).split("_")[0];
            if (StringUtils.isBlank(userName)) {
                throw new ControllerException(ResponseCodeEnum.NO_SUCH_USER);
            }
            return userName;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
