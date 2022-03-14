package com.tuituidan.openhub.service.mergerequest;

import com.tuituidan.openhub.bean.entity.Markdown;
import com.tuituidan.openhub.bean.entity.MergeRequest;
import com.tuituidan.openhub.bean.entity.Project;
import com.tuituidan.openhub.bean.vo.MergeRequestVo;
import com.tuituidan.openhub.repository.MarkdownRepository;
import com.tuituidan.openhub.repository.MergeRequestRepository;
import com.tuituidan.openhub.repository.ProjectRepository;
import com.tuituidan.openhub.service.developer.DeveloperService;
import com.tuituidan.openhub.service.gitlab.GitLabService;
import com.tuituidan.openhub.util.BeanExtUtils;
import com.tuituidan.openhub.util.thread.CompletableUtils;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.Discussion;
import org.gitlab4j.api.models.Note;
import org.gitlab4j.api.models.Position;
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

    @Resource
    private MarkdownRepository markdownRepository;

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

    public void discussion(Integer projectId, Integer[] ids) {
        List<Integer> idList = new ArrayList<>(Arrays.asList(ids));
        List<Markdown> resultList = markdownRepository.findAllById(idList);
        if (resultList.size() == idList.size()) {
            export(resultList.stream().map(Markdown::getMarkdown).collect(Collectors.toList()));
            return;
        }
        idList.removeAll(resultList.stream().map(Markdown::getId).collect(Collectors.toList()));
        resultList.addAll(createMarkdown(idList));
        export(resultList.stream().map(Markdown::getMarkdown).collect(Collectors.toList()));
    }

    private void export(List<String> markdowns) {
        // export;
    }

    private List<Markdown> createMarkdown(List<Integer> ids) {
        List<MergeRequest> mergeRequests = mergeRequestRepository.findAllById(ids);
        List<Markdown> resultList = new ArrayList<>();
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (MergeRequest mergeRequest : mergeRequests) {
            futures.add(CompletableUtils.runAsync(() -> {
                resultList.add(getMarkdown(mergeRequest));
            }));
        }
        CompletableUtils.waitAll(futures);
        return resultList;
    }

    private Markdown getMarkdown(MergeRequest mergeRequest) {
        List<Discussion> discussions = gitLabService.getMergeRequestDiscussions(mergeRequest.getProjectId(), mergeRequest.getMergeId())
                .stream().filter(item -> BooleanUtils.isFalse(item.getIndividualNote()))
                .collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        result.add("-------" + mergeRequest.getSourceBranch() + "------");
        for (Discussion discussion : discussions) {
            List<Note> notes = discussion.getNotes().stream().filter(item -> BooleanUtils.isFalse(item.getSystem()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(notes)) {
                continue;
            }
            Position position = notes.get(0).getPosition();
            Diff diff = gitLabService.getCommitDiffs(mergeRequest.getProjectId(), position.getHeadSha())
                    .stream().filter(item -> StringUtils.equals(item.getNewPath(), position.getNewPath()))
                    .findFirst().orElse(null);

            if (diff != null) {
                String[] lines = diff.getDiff().split("\n");
                List<String> contents = new ArrayList<>();
                Map<Integer, List<String>> maps = new HashMap<>();
                int lastKey = -1;
                for (String line : lines) {
                    String header = StringUtils.substringBetween(line, "@@");
                    if (StringUtils.isNotBlank(header)) {
                        if (lastKey != -1) {
                            maps.put(lastKey, new ArrayList<>(contents));
                            contents.clear();
                        }
                        header = StringUtils.substringAfter(header, "+");
                        header = StringUtils.substringBefore(header, ",");
                        lastKey = Integer.parseInt(header);
                        maps.put(lastKey, Collections.emptyList());
                        continue;
                    }
                    contents.add(line);
                }
                contents.clear();
                Integer lastPos = 0;
                List<Integer> positions = maps.keySet().stream().sorted().collect(Collectors.toList());
                for (Integer pos : positions) {
                    if (pos >= position.getNewLine()) {
                        List<String> temps = maps.get(lastPos);
                        if (CollectionUtils.isNotEmpty(temps)) {
                            contents.addAll(temps);
                        }
                        break;
                    }
                    lastPos = pos;
                }
                result.add("--------代码---------");
                result.addAll(new ArrayList<>(contents));
                result.add("-------讨论-------");
                for (Note note : notes) {
                    result.add(note.getAuthor().getName() + "：" + note.getBody());
                }
            }
        }
        Markdown markdown = new Markdown().setId(mergeRequest.getId()).setMarkdown(StringUtils.join(result, "\n"));
        markdownRepository.save(markdown);
        return markdown;
    }
}
