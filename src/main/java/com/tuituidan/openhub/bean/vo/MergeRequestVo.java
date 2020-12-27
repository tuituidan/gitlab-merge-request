package com.tuituidan.openhub.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * MergeRequestVo.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
@Getter
@Setter
@Accessors(chain = true)
public class MergeRequestVo {

    private Integer id;

    private Integer mergeId;

    private Integer projectId;

    private String sourceBranch;

    private String webUrl;

    private String author;

    private String authorName;

    private Integer discussionCount;

    private Integer changeFiles;

    private Integer changeLines;

    private Integer addLines;

    private Integer deleteLines;

    private String commitSha;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime mergedTime;
}
