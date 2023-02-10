package com.ujs.shop.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ujs.shop.common.dto.UserInfoDTO;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ControllerException;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mundo.wang
 * @date 2023/2/8 15:54
 */


@Component
public class BaseController {

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 从请求头中获取JwtToken
     * @return
     */
    public String getJwtToken() {
        return request.getHeader(ConstantBean.AUTHORIZATION);
    }


    /**
     * 由jwtToken获取生成的随机token
     * @return
     */
    public String getToken() {
        String jwtToken = getJwtToken();
        DecodedJWT decode = JWTHelper.decode(jwtToken);
        if (! jwtToken.equals(decode.getToken())) {
            throw new ControllerException(ResponseCodeEnum.JWT_TOKEN_ERROR);
        }
        return JWTHelper.getToken(jwtToken);
    }

    /**
     * 由token从Redis获取到用户具体信息
     * @return
     */
    public UserInfoDTO getUserInfo() {
        return (UserInfoDTO) redisTemplate.boundValueOps(getToken()).get();
    }


    /**
     * 获取登录用户的用户id
     */
    public String getUserId() {
        UserInfoDTO userInfoDTO = getUserInfo();
        return userInfoDTO.getId();
    }

    /**
     * 获取登录用户的用户名
     */
    public String getUserName() {
        UserInfoDTO userInfoDTO = getUserInfo();
        return userInfoDTO.getUserName();
    }


}
