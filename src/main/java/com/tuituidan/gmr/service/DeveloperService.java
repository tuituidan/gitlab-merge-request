package com.tuituidan.gmr.service;

import com.tuituidan.gmr.bean.entity.Developer;
import com.tuituidan.gmr.bean.vo.DeveloperVo;
import com.tuituidan.gmr.mybatis.mapper.DeveloperMapper;
import com.tuituidan.gmr.util.BeanExtUtils;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.gitlab4j.api.models.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * UserService.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Service
public class DeveloperService {

    @Resource
    private DeveloperMapper developerMapper;

    @Resource
    private GitLabService gitLabService;

    @Cacheable(value = "users")
    public DeveloperVo getDeveloper(String id) {
        Developer developer = developerMapper.selectByPrimaryKey(id);
        return BeanExtUtils.convert(developer, DeveloperVo.class);
    }

    /**
     * 用户登录注册.
     *
     * @param loginId  loginId
     * @param password password
     * @return getDeveloper
     */
    public Developer getDeveloper(String loginId, String password) {
        Developer developer = new Developer().setLoginId(loginId).setPassword(password);
        List<Developer> list = developerMapper.select(developer);
        if (CollectionUtils.isEmpty(list)) {
            developer.setGitlabLoginId(loginId);
            User user = gitLabService.getUser(developer);
            developer.setAvatarUrl(user.getAvatarUrl());
            developer.setName(user.getName());
            developer.setProjectCount(0);
            developer.setMergeRequestCount(0);
            developer.setDiscussionCount(0);
            developer.setNodiscussionCount(0);
            developerMapper.insert(developer);
            return developer;
        }
        return list.get(0);
    }

}
