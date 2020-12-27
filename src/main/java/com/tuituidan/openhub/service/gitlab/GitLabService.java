package com.tuituidan.openhub.service.gitlab;

import com.github.benmanes.caffeine.cache.Cache;
import com.tuituidan.openhub.bean.entity.Developer;
import com.tuituidan.openhub.bean.vo.DeveloperVo;
import com.tuituidan.openhub.bean.vo.ProjectVo;
import com.tuituidan.openhub.exception.WrapperException;
import com.tuituidan.openhub.util.RequestContextUtils;
import com.tuituidan.openhub.util.StringExtUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.Pager;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.Discussion;
import org.gitlab4j.api.models.Label;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestFilter;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * GitLabService.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
@Service
public class GitLabService {

    @Value("${gitlab.server}")
    private String gitlabServer;

    @Resource(name = "gitlabUserProjects")
    private Cache<String, List<ProjectVo>> gitlabUserProjects;

    @Resource(name = "gitlabProjects")
    private Cache<Integer, ProjectVo> gitlabProjects;

    @Resource(name = "gitlabProjectUsers")
    private Cache<Integer, List<DeveloperVo>> gitlabProjectUsers;

    @Resource(name = "cache-gitlabapi")
    private Cache<String, GitLabApi> gitLabApiCache;


    /**
     * 每次请求数据的个数.
     */
    private static final int PAGE_SIZE = 50;


    public List<String> getLabels(Integer projectId) {
        try {
            return gitLabApi().getLabelsApi().getProjectLabels(projectId)
                    .stream().map(Label::getName).collect(Collectors.toList());
        } catch (GitLabApiException ex) {
            throw new WrapperException("gitlab获取当前人员发生错误", ex);
        }
    }

    public List<Discussion> getMergeRequestDiscussions(Integer projectId, Integer mergeId) {
        try {
            return gitLabApi().getDiscussionsApi()
                    .getMergeRequestDiscussions(projectId, mergeId);
        } catch (GitLabApiException ex) {
            throw new WrapperException("gitlab获取讨论记录发生错误", ex);
        }
    }

    public Commit getMergeRequestCommit(Integer projectId, String commitSha) {
        try {
            return gitLabApi().getCommitsApi()
                    .getCommit(projectId, commitSha);
        } catch (GitLabApiException ex) {
            throw new WrapperException("gitlab获取提交记录发生错误", ex);
        }
    }

    public List<Diff> getMergeRequestChanges(Integer projectId, Integer mergeId) {
        try {
            return gitLabApi().getMergeRequestApi()
                    .getMergeRequestChanges(projectId, mergeId).getChanges();
        } catch (GitLabApiException ex) {
            throw new WrapperException("gitlab获取合并请求变更发生错误", ex);
        }
    }

    public List<MergeRequest> getMergeRequest(com.tuituidan.openhub.bean.entity.Project project) {
        try {
            MergeRequestFilter mergeRequestFilter = new MergeRequestFilter();
            mergeRequestFilter.setProjectId(project.getId());
            mergeRequestFilter.setState(Constants.MergeRequestState.MERGED);
            if (StringUtils.isNotBlank(project.getTargetBranch())) {
                mergeRequestFilter.setTargetBranch(project.getTargetBranch());
            }
            if (ArrayUtils.isNotEmpty(project.getLabels())) {
                mergeRequestFilter.setLabels(Arrays.asList(project.getLabels()));
            }
            Pager<MergeRequest> mergeRequests = gitLabApi().getMergeRequestApi()
                    .getMergeRequests(mergeRequestFilter, PAGE_SIZE);
            List<MergeRequest> resultList = mergeRequests.first();
            while (mergeRequests.hasNext()) {
                resultList.addAll(mergeRequests.next());
            }
            return resultList;
        } catch (GitLabApiException ex) {
            throw new WrapperException("gitlab获取合并请求发生错误", ex);
        }
    }


    public List<ProjectVo> getUserProjects() {
        String userId = RequestContextUtils.get().getId();
        return gitlabUserProjects.get(userId, key -> {
            try {
                return gitLabApi().getProjectApi().getMemberProjects().stream()
                        .map(item -> new ProjectVo().setId(item.getId())
                                .setName(item.getName())
                                .setDesc(item.getDescription())
                                .setNameWithSpace(item.getNameWithNamespace())
                        ).collect(Collectors.toList());
            } catch (GitLabApiException ex) {
                throw new WrapperException("gitlab获取人员项目错误", ex);
            }
        });
    }

    public ProjectVo getProject(Integer projectId) {
        return gitlabProjects.get(projectId, key -> {
            try {
                Project project = gitLabApi().getProjectApi().getProject(projectId);
                return new ProjectVo().setId(projectId)
                        .setName(project.getName())
                        .setNameWithSpace(project.getNameWithNamespace())
                        .setDesc(project.getDescription());
            } catch (GitLabApiException ex) {
                throw new WrapperException("gitlab获取项目错误", ex);
            }
        });
    }

    public List<DeveloperVo> getProjectUsers(Integer projectId) {
        return gitlabProjectUsers.get(projectId, key -> {
            try {
                return gitLabApi().getProjectApi().getAllMembers(projectId).stream()
                        .map(item -> new DeveloperVo().setLoginId(item.getUsername())
                                .setAvatarUrl(item.getAvatarUrl())
                                .setName(item.getName()))
                        .collect(Collectors.toList());
            } catch (GitLabApiException ex) {
                throw new WrapperException("gitlab获取项目人员发生错误", ex);
            }
        });
    }


    public List<User> getUser(Set<String> loginIds) {
        try {
            List<User> list = new ArrayList<>();
            for (String loginId : loginIds) {
                list.add(gitLabApi().getUserApi().getUser(loginId));
            }
            return list;
        } catch (GitLabApiException ex) {
            throw new WrapperException("gitlab获取人员集合发生错误", ex);
        }
    }

    public User getCurrentUser(String loginId, String password) {
        try {
            return GitLabApi.oauth2Login(gitlabServer, loginId, password).getUserApi().getCurrentUser();
        } catch (GitLabApiException ex) {
            throw new WrapperException("用户名或密码错误，登录失败", ex);
        }
    }

    private GitLabApi gitLabApi() {
        Developer loginDto = RequestContextUtils.get();
        return gitLabApiCache.get(loginDto.getId(), key -> {
            try {
                return GitLabApi.oauth2Login(gitlabServer, loginDto.getLoginId(),
                        StringExtUtils.decodeBase64(loginDto.getPassword()));
            } catch (Exception ex) {
                throw new WrapperException("gitlab请求发生错误", ex);
            }
        });
    }
}
