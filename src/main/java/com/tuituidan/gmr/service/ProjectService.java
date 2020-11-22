package com.tuituidan.gmr.service;

import com.tuituidan.gmr.bean.entity.User;
import com.tuituidan.gmr.bean.vo.ProjectVo;
import com.tuituidan.gmr.mybatis.mapper.ProjectMapper;
import com.tuituidan.gmr.mybatis.mapper.UserMapper;

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

    @Resource
    private UserMapper userMapper;

    /**
     * 获取当前登录人所属项目集合.
     *
     * @param username 当前登录人
     * @return 当前登录人所属项目集合
     */
    public List<ProjectVo> selectByUser(String username) {
        return projectMapper.selectByUser(username);
    }


    @PostConstruct
    public void save() {
        userMapper.insert(new User().setLoginId("zhujunhan").setName("朱军函").setGitlabLoginId("zhujunhan"));
    }

}
