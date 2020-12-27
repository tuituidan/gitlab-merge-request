package com.tuituidan.openhub.controller;

import com.tuituidan.openhub.bean.dto.LoginDto;
import com.tuituidan.openhub.bean.entity.Developer;
import com.tuituidan.openhub.consts.Consts;
import com.tuituidan.openhub.service.developer.DeveloperService;
import com.tuituidan.openhub.util.CookieUtils;
import com.tuituidan.openhub.util.StringExtUtils;

import javax.annotation.Resource;

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
@RequestMapping(Consts.API_V1 + "/login")
public class LoginController {

    @Resource
    private DeveloperService developerService;

    @GetMapping("/out")
    public String loginout() {
        CookieUtils.removeCookie(Consts.SESSIONID);
        return "redirect:/";
    }

    @PostMapping
    @ResponseBody
    public void login(@RequestBody LoginDto loginDto) {
        Developer developer = developerService.getLoginDeveloper(loginDto.getLoginId(), loginDto.getPassword());
        CookieUtils.addCookie(Consts.SESSIONID, StringExtUtils.encodeBase64(developer.getId()));
    }

}
