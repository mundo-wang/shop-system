package com.suye.test;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.suye.Exception.NoKfcToEatException;
import com.suye.common.CommonContext;
import com.suye.dto.UserInfoDTO;
import com.suye.utils.JWTHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @author sj.w
 */

//@SpringBootTest
public class MyTest {


    @Test
    public void test01() {
//        System.out.println(LocalDateTime.now());
        try {
            throw new NoKfcToEatException("You need $50 for KFC crazy thursday");
        } catch (NoKfcToEatException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test02() {

//        加密
        String token = JWTHelper.sign("张三", "ikun后援会", 3, CommonContext.TOKEN_SECRET, 30000);
        System.out.println(token);

//        解密
        UserInfoDTO userInfoDTO = JWTHelper.getInfo(token);
        System.out.println(userInfoDTO.toString());

//        校验
        DecodedJWT decode = JWTHelper.decode(token);
        System.out.println(token.equals(decode.getToken()));
    }


    @Test
    public void test03() {
        char c = '\u4e00';
        for (int i = 0; i < 200; i++) {
            c = (char) (c + i);
            System.out.print(c);
        }

        System.out.println('\u4ec7');
    }
}
