package com.suye.test;

import com.suye.Exception.NoKfcToEatException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;

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
}
