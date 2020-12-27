package com.tuituidan.openhub.repository;

import com.tuituidan.openhub.bean.entity.Project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * ProjectRepository.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    /**
     * findByDevelopersContains.
     *
     * @param loginId loginId
     * @return List
     */
    @Query(value = "select * from db_gitlab.t_project where ?1=ANY(arr_developers)", nativeQuery = true)
    List<Project> selectByUser(String loginId);

}
