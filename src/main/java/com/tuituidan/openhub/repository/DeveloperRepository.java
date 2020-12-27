package com.tuituidan.openhub.repository;

import com.tuituidan.openhub.bean.entity.Developer;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DeveloperRepository.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
public interface DeveloperRepository extends JpaRepository<Developer, String> {

    /**
     * findByLoginId.
     *
     * @param loginIds loginIds
     * @return Developer
     */
    List<Developer> findByLoginIdIn(Set<String> loginIds);

    /**
     * findByLoginId.
     *
     * @param loginId loginId
     * @return Developer
     */
    Developer findByLoginId(String loginId);
}
