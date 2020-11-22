package com.tuituidan.gmr.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * LoginInterceptor.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Configuration
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        if (request.getServletPath().startsWith("/list")) {
//            response.sendRedirect("/error/noauth");
//        }
        System.out.println(request.getServletPath());
        return true;
    }
}
