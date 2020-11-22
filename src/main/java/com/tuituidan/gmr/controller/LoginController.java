package com.tuituidan.gmr.controller;

import com.tuituidan.gmr.consts.Consts;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * LoginController.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Controller
@RequestMapping(Consts.API_V1)
public class LoginController {

    @PostMapping("/login")
    public String login(HttpServletResponse response, HttpServletRequest request,
                        @RequestParam(value = "username", required = false) String user) {
        Cookie cookie = new Cookie(Consts.AUTH_HEAD_KEY,
                Base64Utils.encodeToString("00000000000000000000000000000000".getBytes(StandardCharsets.UTF_8)));
        // 浏览器关闭失效
        cookie.setMaxAge(-1);
        cookie.setDomain(request.getServerName());
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

}
