package com.tuituidan.openhub.controller;

import com.tuituidan.openhub.bean.vo.DeveloperVo;
import com.tuituidan.openhub.bean.vo.ProjectVo;
import com.tuituidan.openhub.consts.Consts;
import com.tuituidan.openhub.service.gitlab.GitLabService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * GitlabController.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
@RestController
@RequestMapping(Consts.API_V1 + "/gitlab")
public class GitlabController {

    @Resource
    private GitLabService gitLabService;

    @GetMapping("/user/projects")
    public List<ProjectVo> getGitlabProject() {
        return gitLabService.getUserProjects();
    }

    @GetMapping("/projects/{projectId}/users")
    public List<DeveloperVo> getGitlabProjectUsers(@PathVariable("projectId") Integer projectId) {
        return gitLabService.getProjectUsers(projectId);
    }

    @GetMapping("/projects/{projectId}/labels")
    public List<String> getGitlabProjectLabels(@PathVariable("projectId") Integer projectId){
        return gitLabService.getLabels(projectId);
    }
}
