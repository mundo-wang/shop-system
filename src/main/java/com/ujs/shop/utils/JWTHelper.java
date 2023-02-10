package com.ujs.shop.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ujs.shop.common.global.ConstantBean;
import org.apache.commons.lang.StringUtils;

/**
 * @author mundo.wang
 * @date 2023/2/8 14:59
 *
 *
 * JWT相关方法，统一写到这里
 */
public class JWTHelper {

    /**
     * 签名方法，返回JWT字符串
     * @param token
     * @param secret
     * @return
     */
    public static String getJWTToken(String token, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("token", token)
                .withClaim("uuid", ConstantBean.getUUIDKey())
                .sign(algorithm);
    }


    /**
     * 根据JWT获取token
     * @param jwtToken
     * @return
     */
    public static String getToken(String jwtToken) {
        try {
            DecodedJWT jwt = JWT.decode(jwtToken);
            return jwt.getClaim("token").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 检验数字签名，看看jwtToken是否被人篡改
     * @param jwtToken
     * @return
     */
    public static DecodedJWT decode(String jwtToken){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(ConstantBean.SECRET)).build();
        return jwtVerifier.verify(jwtToken);
    }



}
