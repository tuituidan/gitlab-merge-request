package com.tuituidan.gmr.controller;

import com.tuituidan.gmr.service.ProjectService;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

/**
 * ProjectController.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
@RestController
public class ProjectController {

    @Resource
    private ProjectService projectService;



}
