package com.tuituidan.openhub.service.project;

import com.github.benmanes.caffeine.cache.Cache;
import com.tuituidan.openhub.bean.dto.ProjectDto;
import com.tuituidan.openhub.bean.entity.Developer;
import com.tuituidan.openhub.bean.entity.Project;
import com.tuituidan.openhub.bean.vo.ProjectVo;
import com.tuituidan.openhub.repository.MergeRequestRepository;
import com.tuituidan.openhub.repository.ProjectRepository;
import com.tuituidan.openhub.service.developer.DeveloperService;
import com.tuituidan.openhub.service.gitlab.GitLabService;
import com.tuituidan.openhub.util.BeanExtUtils;
import com.tuituidan.openhub.util.RequestUtils;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * ProjectService.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
@Service
public class ProjectService {
    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private GitLabService gitLabService;

    @Resource
    private DeveloperService developerService;

    @Resource(name = "localProjects")
    private Cache<Integer, ProjectVo> localProjects;

    @Resource(name = "localUserProjects")
    private Cache<String, List<ProjectVo>> localUserProjects;

    @Resource
    private MergeRequestRepository mergeRequestRepository;

    public ProjectVo getProject(Integer projectId) {
        return localProjects.get(projectId, key ->
                projectRepository.findById(key)
                        .map(item -> BeanExtUtils.convert(item, ProjectVo.class))
                        .orElseThrow(NullPointerException::new));
    }

    /**
     * 获取当前登录人所属项目集合.
     *
     * @param loginId 当前登录人
     * @return 当前登录人所属项目集合
     */
    public List<ProjectVo> selectByUser(String loginId) {
        return localUserProjects.get(loginId, key -> {
            List<Project> projects = projectRepository.selectByUser(key);
            return projects.stream().map(proj -> {
                ProjectVo item = BeanExtUtils.convert(proj, ProjectVo.class);
                String[] names = Arrays.stream(item.getDevelopers())
                        .map(developerService::getDeveloperByLoginId)
                        .map(Developer::getName).toArray(String[]::new);
                item.setDeveloperNames(names);
                return item;
            }).sorted(Comparator.comparing(ProjectVo::getId)).collect(Collectors.toList());
        });
    }

    /**
     * 保存.
     *
     * @param projectDto projectDto
     */
    public String save(ProjectDto projectDto) {
        Developer loginDto = RequestUtils.getLoginInfo();
        Project existsProj = projectRepository.findById(projectDto.getId()).orElse(null);
        if (existsProj != null) {
            Set<String> existUsers = new HashSet<>(Arrays.asList(existsProj.getDevelopers()));
            if (!existUsers.contains(loginDto.getLoginId())) {
                existUsers.add(loginDto.getLoginId());
                developerService.save(existUsers);
                existsProj.setDevelopers(existUsers.toArray(new String[0]));
                projectRepository.save(existsProj);
                return "项目已存在，已将您添加到该项目";
            }
        }
        ProjectVo projectVo = gitLabService.getProject(projectDto.getId());
        Project project = BeanExtUtils.convert(projectVo, Project.class);
        BeanExtUtils.copyNotNullProperties(projectDto, project);
        project.setCreateUser(loginDto.getLoginId());
        Set<String> developers = new HashSet<>(Arrays.asList(projectDto.getDevelopers()));
        developers.add(loginDto.getLoginId());
        project.setDevelopers(developers.toArray(new String[0]));
        developerService.save(developers);
        projectRepository.save(project);
        localUserProjects.invalidateAll();
        return "保存成功";
    }


    /**
     * delete.
     *
     * @param id id
     */
    public void delete(Integer id) {
        projectRepository.deleteById(id);
        mergeRequestRepository.deleteByProjectId(id);
        localUserProjects.invalidateAll();
    }

}
