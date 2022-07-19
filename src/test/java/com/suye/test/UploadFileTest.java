package com.suye.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author sj.w
 */

@SpringBootTest
public class UploadFileTest {

    @Test
    public void test01() {
        String fileName = "aoligei.jpg";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }
}
