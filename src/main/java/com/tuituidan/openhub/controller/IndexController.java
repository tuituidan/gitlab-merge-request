package com.tuituidan.openhub.controller;

import com.tuituidan.openhub.service.project.ProjectService;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * IndexController.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
@Controller
public class IndexController {


    @Resource
    private ProjectService projectService;

    /**
     * 首页.
     *
     * @return String
     */
    @GetMapping({"/", "index.html"})
    public String index(ModelMap modelMap) {
        modelMap.put("page", "dashboard");
        return "index";
    }

    /**
     * 登录.
     *
     * @return String
     */
    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/projects")
    public String projects(ModelMap modelMap) {
        modelMap.put("page", "projects");
        return "index";
    }

    @GetMapping("/merge-requests/{projectId}")
    public String mergeRequest(@PathVariable("projectId") Integer projectId, ModelMap modelMap) {
        modelMap.put("page", "merge-requests");
        modelMap.put("projectInfo", projectService.getProject(projectId));
        return "index";
    }
}
