package com.tuituidan.gmr.bean.entity;

import com.tuituidan.gmr.mybatis.typehandler.ArrayTypeHandler;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * Project.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "T_PROJECT", schema = "DB_GITLAB")
public class Project implements Serializable, BaseEntity<Project> {

    private static final long serialVersionUID = -510677508809302821L;
    @Id
    @Column(name = "C_ID")
    private String id;

    @Column(name = "C_GITLAB_URL")
    private String gitlabUrl;

    @Column(name = "N_PROJECT_ID")
    private Integer projectId;

    @Column(name = "C_PROJECT_NAME")
    private String projectName;

    @Column(name = "C_PROJECT_DESC")
    private String projectDesc;

    @Column(name = "C_LOGINID")
    private String loginId;

    @Column(name = "C_PASSWORD")
    private String password;

    @Column(name = "C_PRIVATE_TOKEN")
    private String privateToken;

    @Column(name = "ARR_DEVELOPERS")
    @ColumnType(typeHandler = ArrayTypeHandler.class)
    private String[] developers;

    @Column(name = "C_CREATOR")
    private String creator;

    @Column(name = "DT_CREATE_TIME")
    private LocalDateTime createTime;

    @Column(name = "DT_UPDATE_TIME")
    private LocalDateTime updateTime;
}
