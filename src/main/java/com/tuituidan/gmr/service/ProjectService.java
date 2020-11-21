package com.tuituidan.gmr.service;

import com.tuituidan.gmr.bean.entity.Project;
import com.tuituidan.gmr.bean.vo.ProjectVo;
import com.tuituidan.gmr.mybatis.mapper.ProjectMapper;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * ProjectService.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
@Service
public class ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    /**
     * 获取当前登录人所属项目集合.
     *
     * @param username 当前登录人
     * @return 当前登录人所属项目集合
     */
    public List<ProjectVo> selectByCreator(String username) {
        return projectMapper.selectByCreator(username);
    }


    @PostConstruct
    public void save() {
        List<Project> projects = projectMapper.selectAll();
    }

}
