package com.ujs.shop.filter;

import com.alibaba.fastjson.JSON;
import com.ujs.shop.common.enums.NoFilterPathEnum;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.global.ResponseBean;
import com.ujs.shop.utils.JWTHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/2/8 14:40
 *
 * 判断用户是否跳过登录直接访问内部页面
 */


/**
 * urlPatterns 配置你需要拦截哪些路径，这里我们拦截所有路径
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {


    /**
     * 路径匹配器，支持通配符
     */
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();


    @Autowired
    private RedisTemplate redisTemplate;


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
        String requestURI = request.getRequestURI();
        boolean result = checkUri(getNoFilterUris(), requestURI);

//        路径在白名单里，直接放行
        if (result) {
            filterChain.doFilter(request, response);
            return;
        }

//        这里如果采用抛出异常，无法被全局异常捕捉器捕捉到。我们使用输出流的方式返回给前端。
//        更改浏览器解码规则，按json格式输出给前端。
//        注意！这里更改response解码规则要放到if语句里面，不然如果登录没问题，这里改了会影响后面响应的处理。
        String jwtToken = request.getHeader(ConstantBean.AUTHORIZATION);
        if (jwtToken == null || jwtToken.equals("")) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(ResponseBean.fail(ResponseCodeEnum.NO_LOGIN_MSG)));
            return;
        }
        if (! JWTHelper.decode(jwtToken)) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(ResponseBean.fail(ResponseCodeEnum.JWT_TOKEN_ERROR)));
            return;
        }
        String token = JWTHelper.getToken(jwtToken);
        if (! redisTemplate.hasKey(token)) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(ResponseBean.fail(ResponseCodeEnum.OVERDUE)));
            return;
        }

//        检测到已登录，放行
        filterChain.doFilter(request, response);

    }


    /**
     * 所有无需过滤路径的集合
     * @return
     */
    private List<String> getNoFilterUris() {
        List<String> list = new ArrayList();
        list.add(NoFilterPathEnum.PATH1.getPath());
//        list.add(NoFilterPathEnum.PATH2.getPath());
        list.add(NoFilterPathEnum.PATH3.getPath());
        list.add(NoFilterPathEnum.PATH4.getPath());
        return list;
    }


    /**
     * 路径匹配，检测本次请求是否在路径白名单里
     * @param uris
     * @param requsetURI
     * @return
     */
    public boolean checkUri(List<String> uris, String requsetURI) {
        for (String uri : uris) {
            boolean match = PATH_MATCHER.match(uri, requsetURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
