package com.tuituidan.gmr.mybatis.mapper;

import com.tuituidan.gmr.bean.entity.Project;
import com.tuituidan.gmr.bean.vo.ProjectVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * ProjectMapper.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * selectByCreator.
     *
     * @param username username
     * @return List
     */
    List<ProjectVo> selectByCreator(@Param("username") String username);

}
