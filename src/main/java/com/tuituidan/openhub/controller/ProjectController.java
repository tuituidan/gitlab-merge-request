package com.tuituidan.openhub.controller;

import com.tuituidan.openhub.bean.dto.ProjectDto;
import com.tuituidan.openhub.consts.Consts;
import com.tuituidan.openhub.service.project.ProjectService;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(Consts.API_V1 + "/projects")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProjectDto project) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.save(project));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id,@RequestBody ProjectDto project) {
        projectService.save(project);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
