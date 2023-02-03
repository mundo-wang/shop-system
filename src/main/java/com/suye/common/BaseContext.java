package com.suye.common;

/**
 * @author sj.w
 * 基于ThreadLocal封装工具类，用于保存和获取当前登录用户的id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void main(String[] args) {
        float f = 3.4F;
        System.out.println(f);
    }

}
