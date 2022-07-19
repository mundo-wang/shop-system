package com.suye.filter;

import com.alibaba.fastjson.JSON;
import com.suye.common.BaseContext;
import com.suye.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sj.w
 */

/**
 * 检查用户是否已经完成登录
 */

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

//    路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        1.获取本次请求的uri
        String requestURI = request.getRequestURI();

        log.info("拦截到请求：{}", request.getRequestURI());

//        2.定义不需要过滤器处理的请求
        String[] uris = new String[]{
            "/employee/login", "/employee/logout", "/backend/**", "/front/**",
                "/common/**", "/user/sendMsg", "/user/login"
        };

//        3.判断本次请求是否需要处理
        boolean check = check(uris, requestURI);

//        4.如果不需要处理，则直接放行
        if (check) {
            filterChain.doFilter(request, response);
            log.info("请求：{} 无需处理，可放行", request.getRequestURI());
            return;
        }

//        5-1.判断后台登录状态，如果已登录，则直接放行（我们在login方法把登录信息写进session了）
        if (request.getSession().getAttribute("employee") != null) {

            Long empId = (Long) request.getSession().getAttribute("employee");
//            ThreadLocal的应用，把登录人的id放入当前线程的线程变量中
            BaseContext.setCurrentId(empId);

//            long id = Thread.currentThread().getId();
//            log.info("线程id为：" + id);
            filterChain.doFilter(request, response);
            return;
        }


//        5-2.判断移动端登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("user") != null) {

            Long userId = (Long) request.getSession().getAttribute("user");
//            ThreadLocal的应用，把登录人的id放入当前线程的线程变量中
            BaseContext.setCurrentId(userId);

//            long id = Thread.currentThread().getId();
//            log.info("线程id为：" + id);
            filterChain.doFilter(request, response);
            return;
        }

//        6.如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

    }


    /**
     * 路径匹配，检测本次请求是否需要放行
     * @param uris
     * @param requsetURI
     * @return
     */
    public boolean check(String[] uris, String requsetURI) {
        for (String uri : uris) {
            boolean match = PATH_MATCHER.match(uri, requsetURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
