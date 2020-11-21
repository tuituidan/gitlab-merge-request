package com.tuituidan.gmr.controller;

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


    @GetMapping({"/", "index.html"})
    public String index(ModelMap modelMap) {

        return "index";
    }

    @GetMapping("/layout")
    public String layout() {

        return "layout";
    }

    @GetMapping("/list")
    public String list() {

        return "list";
    }


}
