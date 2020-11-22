package com.tuituidan.gmr.service;

import com.tuituidan.gmr.bean.entity.Developer;
import com.tuituidan.gmr.exception.WrapperException;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * GitLabService.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Service
public class GitLabService {

    @Value("${gitlab.server}")
    private String gitlabUrl;

    public User getUser(Developer developer) {
        return request(developer, gitLabApi -> {
            try {
                return gitLabApi.getUserApi().getCurrentUser();
            } catch (GitLabApiException ex) {
                throw new WrapperException("gitlab获取当前人员发生错误", ex);
            }
        });
    }


    private <T> T request(Developer developer, Function<GitLabApi, T> function) {
        try (GitLabApi gitLabApi = StringUtils.isNotBlank(developer.getPrivateToken())
                ? new GitLabApi(gitlabUrl, developer.getPrivateToken())
                : GitLabApi.oauth2Login(gitlabUrl, developer.getGitlabLoginId(), developer.getPassword())) {
            return function.apply(gitLabApi);
        } catch (Exception ex) {
            throw new WrapperException("gitlab请求发生错误", ex);
        }
    }

}
