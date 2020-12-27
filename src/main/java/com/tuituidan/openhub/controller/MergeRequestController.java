package com.tuituidan.openhub.controller;

import com.tuituidan.openhub.bean.vo.MergeRequestVo;
import com.tuituidan.openhub.consts.Consts;
import com.tuituidan.openhub.service.mergerequest.MergeRequestService;
import com.tuituidan.openhub.util.RequestUtils;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * MergeRequestController.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
@RestController
@RequestMapping(Consts.API_V1 + "/projects/{projectId}/merge-request")
public class MergeRequestController {

    @Resource
    private MergeRequestService mergeRequestService;

    @GetMapping("/actions/pull")
    public ResponseEntity<Void> pullMergeRequests(@PathVariable Integer projectId) {
        mergeRequestService.pullMergeRequests(projectId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<MergeRequestVo>> select(@PathVariable Integer projectId) {
        return ResponseEntity.ok(mergeRequestService.select(projectId, RequestUtils.getPageable()));
    }

    @GetMapping("/discussion")
    public ResponseEntity<Void> discussion(@PathVariable Integer projectId,
                                           @RequestParam Integer[] mergeIds) {
        mergeRequestService.discussion(projectId, mergeIds);
        return ResponseEntity.ok().build();
    }
}
