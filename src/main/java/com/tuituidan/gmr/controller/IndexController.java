package com.tuituidan.gmr.controller;

import com.tuituidan.gmr.bean.entity.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * IndexController.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
@Controller
public class IndexController {


    /**
     * 首页.
     *
     * @param modelMap modelMap
     * @return String
     */
    @GetMapping({"/", "index.html"})
    public String index(ModelMap modelMap) {
        modelMap.put("userInfo", new User().setName("朱军函"));
        return "index";
    }
}
