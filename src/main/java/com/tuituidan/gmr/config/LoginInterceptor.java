package com.tuituidan.gmr.config;

import com.tuituidan.gmr.consts.Consts;
import com.tuituidan.gmr.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;
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

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Cookie cookie = Optional.ofNullable(request.getCookies()).map(Arrays::stream)
                .map(item -> item.filter(item2 -> StringUtils.equals(item2.getName(), Consts.AUTH_HEAD_KEY))
                        .findFirst()).flatMap(item -> item).orElse(null);
        if (cookie == null) {
            response.sendRedirect("/login");
            return false;
        }
        String userId = IOUtils.toString(Base64Utils.decodeFromString(cookie.getValue()),
                StandardCharsets.UTF_8.name());
        request.setAttribute("userInfo", userService.getUserInfo(userId));
        return true;
    }
}
