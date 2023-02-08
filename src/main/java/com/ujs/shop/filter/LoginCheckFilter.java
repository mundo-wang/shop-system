package com.ujs.shop.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mundo.wang
 * @date 2023/2/8 14:40
 *
 * 判断用户是否跳过登录直接访问内部页面
 */



public class LoginCheckFilter implements Filter {


    /**
     * 所有请求都会进入到这个过滤器
     * @param servletRequest
     * @param servletResponse
     * @param filterChain 过滤器的链路，调用这个方法的时候代表这个过滤器放行了请求
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

    }
}
