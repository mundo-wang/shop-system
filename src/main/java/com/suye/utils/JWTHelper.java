package com.suye.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.suye.common.CommonContext;
import com.suye.dto.UserInfoDTO;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @author mundo.wang
 * @date 2022/10/12 19:03
 */
public class JWTHelper {

    public static String sign(String userName, String deptName, Integer userId, String secret, long time) {
        Date date = new Date(System.currentTimeMillis() + time);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String userIdString = null;
        if (userId != null) {
            userIdString = userId.toString();
        }
        // 附带username信息
        return JWT.create().withClaim("userName", userName)
                .withClaim("deptName", deptName)
                .withClaim("userId", userIdString)
                .withExpiresAt(date).sign(algorithm);
    }


    public static UserInfoDTO getInfo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserName(jwt.getClaim("userName").asString());
            userInfoDTO.setDeptName(jwt.getClaim("deptName").asString());
            String userId = jwt.getClaim("userId").asString();
            if (StringUtils.isNotBlank(userId)) {
                userInfoDTO.setUserId(Integer.valueOf(userId));
            }
            return userInfoDTO;
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    public static DecodedJWT decode(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(CommonContext.TOKEN_SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;
    }



}
