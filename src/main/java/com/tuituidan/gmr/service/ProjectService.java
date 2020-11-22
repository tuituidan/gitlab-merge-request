package com.tuituidan.gmr.service;

import com.tuituidan.gmr.bean.dto.ProjectDto;
import com.tuituidan.gmr.bean.entity.Project;
import com.tuituidan.gmr.bean.vo.ProjectVo;
import com.tuituidan.gmr.mybatis.mapper.ProjectMapper;
import com.tuituidan.gmr.util.BeanExtUtils;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
    public List<ProjectVo> selectByUser(String username) {
        return projectMapper.selectByUser(username);
    }

    /**
     * 保存.
     *
     * @param projectDto projectDto
     */
    public void save(ProjectDto projectDto) {
        Project project = BeanExtUtils.convert(projectDto, Project.class);
        if (StringUtils.isBlank(project.getId())) {
            projectMapper.insert(project);
        } else {
            projectMapper.updateByPrimaryKeySelective(project);
        }
    }


    /**
     * delete.
     *
     * @param id id
     */
    public void delete(String id) {
        projectMapper.deleteByPrimaryKey(id);
    }

}
