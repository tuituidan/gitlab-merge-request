package com.tuituidan.openhub.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.tuituidan.openhub.bean.entity.Developer;
import com.tuituidan.openhub.bean.vo.ProjectVo;
import com.tuituidan.openhub.consts.Consts;
import com.tuituidan.openhub.service.developer.DeveloperService;
import com.tuituidan.openhub.util.RequestUtils;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DeveloperController.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
@RestController
@RequestMapping(Consts.API_V1 + "/developer")
public class DeveloperController {

    @Resource(name = "localUserProjects")
    private Cache<String, List<ProjectVo>> localUserProjects;

    @Resource(name = "gitlabUserProjects")
    private Cache<String, List<ProjectVo>> gitlabUserProjects;

    @Resource(name = "localLoginIdDevelopers")
    private Cache<String, Developer> localLoginIdDevelopers;

    @Resource
    private DeveloperService developerService;

    @PatchMapping("/cache")
    public ResponseEntity<Void> cache() {
        Developer developer = RequestUtils.getLoginInfo();
        localUserProjects.invalidate(developer.getLoginId());
        gitlabUserProjects.invalidate(developer.getId());
        localLoginIdDevelopers.invalidate(developer.getLoginId());
        return ResponseEntity.noContent().build();
    }
}
