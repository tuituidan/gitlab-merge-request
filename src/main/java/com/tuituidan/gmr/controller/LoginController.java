package com.tuituidan.gmr.controller;

import com.tuituidan.gmr.bean.entity.Developer;
import com.tuituidan.gmr.consts.Consts;
import com.tuituidan.gmr.service.DeveloperService;

import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;
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

    @Resource
    private DeveloperService developerService;

    @PostMapping("/login")
    public String login(HttpServletResponse response, HttpServletRequest request,
                        @RequestParam(value = "username") String loginId,
                        @RequestParam(value = "password") String password) {

        Developer developer = developerService.getDeveloper(loginId, password);
        Cookie cookie = new Cookie(Consts.AUTH_HEAD_KEY,
                Base64Utils.encodeToString(developer.getId().getBytes(StandardCharsets.UTF_8)));
        // 浏览器关闭失效
        cookie.setMaxAge(-1);
        cookie.setDomain(request.getServerName());
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

}
