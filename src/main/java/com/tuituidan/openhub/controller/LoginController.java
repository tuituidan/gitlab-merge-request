package com.tuituidan.openhub.controller;

import com.tuituidan.openhub.bean.dto.LoginDto;
import com.tuituidan.openhub.service.developer.DeveloperService;
import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * LoginController.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Controller
@RequestMapping
public class LoginController {

    @Resource
    private DeveloperService developerService;


    /**
     * 登录.
     *
     * @return String
     */
    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }


    @PostMapping("/login")
    @ResponseBody
    public void login(@RequestBody LoginDto loginDto) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        subject.getSession().stop();
        UsernamePasswordToken token = new UsernamePasswordToken(loginDto.getLoginId(), loginDto.getPassword());
        SecurityUtils.getSubject().login(token);
    }

}
