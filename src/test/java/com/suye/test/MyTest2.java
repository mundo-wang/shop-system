package com.suye.test;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * @author sj.w
 */
public class MyTest2 {

    @Test
    public void test01() {
        String name = "";
        boolean b = StringUtils.isNotEmpty(name);
        System.out.println(b);
    }
}
