package com.bjpowernode.money.config;

import com.bjpowernode.money.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author tzsang
 * @create 2022-06-14 15:53
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截路径
        String[] addPathPatterns = {"/loan/**"};
        //排除拦截路径
        String[] excludePathPatterns = {
                "/loan/loan",
                "/loan/loanInfo",
                "/loan/register",
                "/loan/page/login",
                "/loan/page/register",
                "/loan/page/login",
                "/loan/login-captcha",
                "/loan/checkPhone",
                "/loan/code"
        };
        //注册拦截器
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns(addPathPatterns)
                .excludePathPatterns(excludePathPatterns);
    }
}
