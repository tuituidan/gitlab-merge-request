package com.tuituidan.openhub.service.developer;

import com.github.benmanes.caffeine.cache.Cache;
import com.tuituidan.openhub.bean.entity.Developer;
import com.tuituidan.openhub.bean.vo.DeveloperVo;
import com.tuituidan.openhub.repository.DeveloperRepository;
import com.tuituidan.openhub.service.gitlab.GitLabService;
import com.tuituidan.openhub.util.AesGcmUtils;
import com.tuituidan.openhub.util.BeanExtUtils;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    private DeveloperRepository developerRepository;

    @Resource
    private GitLabService gitLabService;

    @Resource(name = "localDevelopers")
    private Cache<String, Developer> localDevelopers;

    @Resource(name = "localLoginIdDevelopers")
    private Cache<String, Developer> localLoginIdDevelopers;

    @Resource(name = "cache-gitlabapi")
    private Cache<String, GitLabApi> gitLabApiCache;

    public DeveloperVo getDeveloperInfo(String id) {
        return BeanExtUtils.convert(getDeveloper(id), DeveloperVo.class);
    }

    public Developer getDeveloper(String id) {
        return localDevelopers.get(id, key -> developerRepository.findById(id).orElse(null));
    }

    public Developer getDeveloperByLoginId(String loginId) {
        return localLoginIdDevelopers.get(loginId, key -> developerRepository.findByLoginId(key));
    }

    public void save(Set<String> needSaveIds) {
        synchronized (this) {
            List<String> existsIds = developerRepository.findByLoginIdIn(needSaveIds)
                    .stream().map(Developer::getLoginId).collect(Collectors.toList());
            needSaveIds.removeAll(existsIds);
            if (CollectionUtils.isEmpty(needSaveIds)) {
                return;
            }
            List<Developer> saveList = gitLabService.getUser(needSaveIds).stream()
                    .map(user -> new Developer()
                            .setLoginId(user.getUsername())
                            .setAvatarUrl(user.getAvatarUrl())
                            .setName(user.getName()).setProjectCount(0).setMergeRequestCount(0)
                            .setDiscussionCount(0).setNodiscussionCount(0))
                    .collect(Collectors.toList());
            developerRepository.saveAll(saveList);
        }
    }

    /**
     * 用户登录注册.
     *
     * @param loginId loginId
     * @param password password
     * @return getDeveloper
     */
    public Developer getLoginDeveloper(String loginId, String password) {
        Assert.hasText(loginId, "loginId不能为空");
        Assert.hasText(password, "password不能为空");
        Developer developer = developerRepository.findByLoginId(loginId);
        // 使用aes加密，因为获取gitlab数据的时候还需要解密并传到gitlab验证（GCM模式，同一个密码每次加密后的密文都不同，更加安全）
        String encodePassword = AesGcmUtils.encrypt(password);
        if (developer != null && StringUtils.isNotBlank(developer.getPassword())
                && StringUtils.equals(encodePassword, developer.getPassword())) {
            // 本地密码匹配上就直接登录成功（假设gitlab改了密码，其实原来的密码在这里就还能用）
            return developer;
        }
        // 匹配不上再走gitlab验证
        User user = gitLabService.getCurrentUser(loginId, password);
        if (developer == null) {
            developer = new Developer();
            developer.setLoginId(loginId);
            developer.setPassword(encodePassword);
            developer.setAvatarUrl(user.getAvatarUrl());
            developer.setName(user.getName());
            developer.setProjectCount(0);
            developer.setMergeRequestCount(0);
            developer.setDiscussionCount(0);
            developer.setNodiscussionCount(0);
            developerRepository.save(developer);
            return developer;
        }
        developer.setPassword(encodePassword);
        developerRepository.save(developer);
        return developer;
    }

}
