package com.bjpowernode.money.interceptor;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tzsang
 * @create 2022-06-14 15:50
 */
@Repository
public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUser = request.getSession().getAttribute("loginUser");
        if (loginUser==null){
            response.sendRedirect("/money/index");
            return false;
        }
        return true;
    }
}
