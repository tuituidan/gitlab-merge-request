package com.tuituidan.openhub.controller;

import com.tuituidan.openhub.bean.entity.Developer;
import com.tuituidan.openhub.service.developer.DeveloperService;
import com.tuituidan.openhub.service.project.ProjectService;
import com.tuituidan.openhub.util.RequestUtils;
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
    private DeveloperService developerService;

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
        setGlobalInfo(modelMap);
        return "index";
    }

    @GetMapping("/projects")
    public String projects(ModelMap modelMap) {
        modelMap.put("page", "projects");
        setGlobalInfo(modelMap);
        return "index";
    }

    @GetMapping("/merge-requests/{projectId}")
    public String mergeRequest(@PathVariable("projectId") Integer projectId, ModelMap modelMap) {
        modelMap.put("page", "merge-requests");
        modelMap.put("projectInfo", projectService.getProject(projectId));
        setGlobalInfo(modelMap);
        return "index";
    }

    private void setGlobalInfo(ModelMap modelMap){
        Developer developer = RequestUtils.getLoginInfo();
        if (developer != null) {
            modelMap.put("userInfo", developerService.getDeveloperInfo(developer.getId()));
            modelMap.put("projects", projectService.selectByUser(developer.getLoginId()));
        }
    }
}
