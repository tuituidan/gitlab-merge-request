package com.tuituidan.openhub.repository;

import com.tuituidan.openhub.bean.entity.Markdown;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MarkdownRepository.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/27
 */
public interface MarkdownRepository extends JpaRepository<Markdown, Integer> {
}
