package com.tuituidan.gmr.controller;

import com.tuituidan.gmr.bean.dto.ProjectDto;
import com.tuituidan.gmr.bean.vo.ProjectVo;
import com.tuituidan.gmr.consts.Consts;
import com.tuituidan.gmr.service.ProjectService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProjectController.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
@RestController
@RequestMapping(Consts.API_V1)
public class ProjectController {

    @Resource
    private ProjectService projectService;

    /**
     * 根据登录人查询列表.
     *
     * @param userName 登录用户
     * @return List
     */
    @GetMapping("/{username}/projects")
    public ResponseEntity<List<ProjectVo>> selectByUser(@PathVariable("username") String userName) {
        return ResponseEntity.ok(projectService.selectByUser(userName));
    }

    @PostMapping("/projects")
    public ResponseEntity<Void> create(@RequestBody ProjectDto project) {
        projectService.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/projects/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody ProjectDto project) {
        project.setId(id);
        projectService.save(project);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
