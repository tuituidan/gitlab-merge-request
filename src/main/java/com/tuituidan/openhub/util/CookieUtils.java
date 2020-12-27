package com.tuituidan.openhub.util;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * CookieUtils.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
@UtilityClass
public class CookieUtils {

    /**
     * getCookie.
     *
     * @param cookieName cookieName
     * @return String
     */
    public static String getCookie(String cookieName) {
        return Arrays.stream(Optional.ofNullable(RequestUtils.getRequest())
                .map(HttpServletRequest::getCookies).orElse(new Cookie[]{}))
                .filter(item -> StringUtils.equals(item.getName(), cookieName))
                .findFirst().map(Cookie::getValue).orElse(null);
    }

    /**
     * 删除cookie.
     *
     * @param cookieName cookieName
     */
    public static void removeCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setDomain(RequestUtils.getRequest().getServerName());
        cookie.setPath("/");
        RequestUtils.getResponse().addCookie(cookie);
    }

    /**
     * 添加cookie.
     *
     * @param cookieName  cookieName
     * @param cookieValue cookieValue
     */
    public static void addCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        // 浏览器关闭失效
        cookie.setMaxAge(-1);
        cookie.setDomain(RequestUtils.getRequest().getServerName());
        cookie.setPath("/");
        RequestUtils.getResponse().addCookie(cookie);
    }
}
