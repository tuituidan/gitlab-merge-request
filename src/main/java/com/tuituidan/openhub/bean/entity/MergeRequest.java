package com.tuituidan.openhub.bean.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Project.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/23
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "T_MERGE_REQUEST", schema = "DB_GITLAB")
@DynamicUpdate
@DynamicInsert
public class MergeRequest implements Serializable {

    private static final long serialVersionUID = -2137539764084789683L;

    @Id
    @Column(name = "N_ID")
    private Integer id;

    @Column(name = "N_MERGE_ID")
    private Integer mergeId;

    @Column(name = "N_PROJECT_ID")
    private Integer projectId;

    @Column(name = "C_SOURCE_BRANCH")
    private String sourceBranch;

    @Column(name = "C_WEB_URL")
    private String webUrl;

    @Column(name = "C_AUTHOR")
    private String author;

    @Column(name = "N_DISCUSSION_COUNT")
    private Integer discussionCount;

    @Column(name = "N_CHANGE_FILES")
    private Integer changeFiles;

    @Column(name = "N_CHANGE_LINES")
    private Integer changeLines;

    @Column(name = "N_ADD_LINES")
    private Integer addLines;

    @Column(name = "N_DELETE_LINES")
    private Integer deleteLines;

    @Column(name = "DT_MERGED_TIME")
    private LocalDateTime mergedTime;

    @CreationTimestamp
    @Column(name = "DT_CREATE_TIME")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "DT_UPDATE_TIME")
    private LocalDateTime updateTime;
}
