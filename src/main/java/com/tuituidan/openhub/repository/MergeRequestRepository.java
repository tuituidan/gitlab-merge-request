package com.tuituidan.openhub.repository;

import com.tuituidan.openhub.bean.entity.MergeRequest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * MergeRequestRepository.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
public interface MergeRequestRepository extends JpaRepository<MergeRequest, Integer> {

    /**
     * findByProjectId.
     *
     * @param projectId projectId
     * @return List
     */
    List<MergeRequest> findByProjectId(Integer projectId);

    /**
     * selectAllId.
     *
     * @param projectId projectId
     * @return List
     */
    @Query("select u.id from MergeRequest u where u.projectId=?1")
    List<Integer> selectAllId(Integer projectId);

    /**
     * deleteByProjectId.
     *
     * @param projectId projectId
     */
    void deleteByProjectId(Integer projectId);
}
