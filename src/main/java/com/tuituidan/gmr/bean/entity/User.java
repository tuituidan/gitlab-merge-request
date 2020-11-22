package com.tuituidan.gmr.bean.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * User.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "T_USER", schema = "DB_GITLAB")
public class User implements Serializable, BaseEntity<User> {
    private static final long serialVersionUID = -960213583721959880L;

    @Id
    @Column(name = "C_ID")
    private String id;

    @Column(name = "C_LOGINID")
    private String loginId;

    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_PASSWORD")
    private String password;

    @Column(name = "C_AVATAR_URL")
    private String avatarUrl;

    @Column(name = "C_GITLAB_LOGINID")
    private String gitlabLoginId;

    @Column(name = "N_PROJECT_COUNT")
    private String projectCount;

    @Column(name = "N_MERGE_REQUEST_COUNT")
    private String mergeRequestCount;

    @Column(name = "N_DISCUSSION_COUNT")
    private String discussionCount;

    @Column(name = "N_NODISCUSSION_COUNT")
    private String nodiscussionCount;

    @Column(name = "DT_CREATE_TIME")
    private LocalDateTime createTime;

    @Column(name = "DT_UPDATE_TIME")
    private LocalDateTime updateTime;
}
