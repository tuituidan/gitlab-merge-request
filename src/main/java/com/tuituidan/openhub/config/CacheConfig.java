package com.tuituidan.openhub.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tuituidan.openhub.bean.entity.Developer;
import com.tuituidan.openhub.bean.vo.DeveloperVo;
import com.tuituidan.openhub.bean.vo.ProjectVo;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.gitlab4j.api.GitLabApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CacheConfig.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
@Configuration
public class CacheConfig {


    @Bean("localUserProjects")
    public Cache<String, List<ProjectVo>> localUserProjects() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofDays(1))
                .build();
    }

    @Bean("gitlabUserProjects")
    public Cache<String, List<ProjectVo>> gitlabUserProjects() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(1))
                .build();
    }

    @Bean("gitlabProjects")
    public Cache<Integer, ProjectVo> gitlabProjects() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(1))
                .build();
    }

    @Bean("localProjects")
    public Cache<Integer, ProjectVo> localProjects() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofDays(1))
                .build();
    }


    @Bean("gitlabProjectUsers")
    public Cache<Integer, List<DeveloperVo>> gitlabProjectUsers() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(1))
                .build();
    }


    @Bean("localDevelopers")
    public Cache<String, Developer> localDevelopers() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofDays(1))
                .build();
    }

    @Bean("localLoginIdDevelopers")
    public Cache<String, Developer> localLoginIdDevelopers() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofDays(1))
                .build();
    }

    @Bean("cache-gitlabapi")
    public Cache<String, GitLabApi> gitLabApiCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(1))
                .removalListener((key, value, cause) -> {
                    Optional.ofNullable((GitLabApi)value).ifPresent(GitLabApi::close);
                })
                .build();
    }
}
