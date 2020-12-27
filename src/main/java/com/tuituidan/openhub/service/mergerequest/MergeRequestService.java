package com.tuituidan.openhub.service.mergerequest;

import com.tuituidan.openhub.bean.entity.MergeRequest;
import com.tuituidan.openhub.bean.entity.Project;
import com.tuituidan.openhub.bean.vo.MergeRequestVo;
import com.tuituidan.openhub.repository.MergeRequestRepository;
import com.tuituidan.openhub.repository.ProjectRepository;
import com.tuituidan.openhub.service.developer.DeveloperService;
import com.tuituidan.openhub.service.gitlab.GitLabService;
import com.tuituidan.openhub.util.BeanExtUtils;
import com.tuituidan.openhub.util.CompletableUtils;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.Discussion;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * MergeRequestService.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
@Service
public class MergeRequestService {

    @Resource
    private MergeRequestRepository mergeRequestRepository;

    @Resource
    private GitLabService gitLabService;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private DeveloperService developerService;

    public void pullMergeRequests(Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(NullPointerException::new);
        List<Integer> existsIds = mergeRequestRepository.selectAllId(projectId);
        List<MergeRequestVo> resultList = gitLabService.getMergeRequest(project)
                .stream().filter(item -> !existsIds.contains(item.getId())).map(item ->
                        new MergeRequestVo()
                                .setId(item.getId()).setMergeId(item.getIid()).setProjectId(item.getProjectId())
                                .setSourceBranch(item.getSourceBranch()).setAuthor(item.getAuthor().getUsername())
                                .setMergedTime(
                                        item.getMergedAt().toInstant().atZone(ZoneId.systemDefault())
                                                .toLocalDateTime())
                                .setWebUrl(item.getWebUrl()).setDiscussionCount(item.getUserNotesCount())
                                .setCommitSha(item.getMergeCommitSha()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(resultList)) {
            return;
        }
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (MergeRequestVo item : resultList) {
            futures.add(CompletableUtils.runAsync(() -> {
                List<Diff> mergeRequestChanges = gitLabService
                        .getMergeRequestChanges(item.getProjectId(), item.getMergeId());
                item.setChangeFiles(mergeRequestChanges.size());
                Commit commit = gitLabService
                        .getMergeRequestCommit(item.getProjectId(), item.getCommitSha());
                item.setChangeLines(commit.getStats().getTotal());
                item.setAddLines(commit.getStats().getAdditions());
                item.setDeleteLines(commit.getStats().getDeletions());
                if (item.getDiscussionCount() <= 0) {
                    return;
                }
                // 之前或者到的只是讨论数，这里才是获取真实的问题数
                List<Discussion> discussionList = gitLabService
                        .getMergeRequestDiscussions(item.getProjectId(), item.getMergeId()).stream()
                        .filter(discussion -> !discussion.getIndividualNote()).collect(Collectors.toList());
                // todo 获取讨论内容
                item.setDiscussionCount(discussionList.size());
            }));
        }
        futures.add(CompletableUtils.runAsync(() ->
                developerService.save(resultList.stream().map(MergeRequestVo::getAuthor).collect(Collectors.toSet()))
        ));
        CompletableUtils.waitAll(futures);
        List<MergeRequest> saveList = resultList.stream().map(item -> BeanExtUtils.convert(item, MergeRequest.class))
                .collect(Collectors.toList());
        mergeRequestRepository.saveAll(saveList);
    }

    public Page<MergeRequestVo> select(Integer projectId, Pageable pageable) {
        Page<MergeRequest> list = mergeRequestRepository
                .findAll(Example.of(new MergeRequest().setProjectId(projectId)), pageable);
        return list.map(item -> {
            MergeRequestVo vo = BeanExtUtils.convert(item, MergeRequestVo.class);
            vo.setAuthorName(developerService.getDeveloperByLoginId(item.getAuthor()).getName());
            return vo;
        });
    }

    public void discussion(Integer projectId, Integer[] mergeIds){

    }
}
