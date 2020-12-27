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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

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
public class Project implements Serializable{

    private static final long serialVersionUID = -510677508809302821L;
    @Id
    @Column(name = "N_ID")
    private Integer id;

    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_NAME_SPACE")
    private String nameWithSpace;

    @Column(name = "C_DESC")
    private String desc;

    @Column(name = "C_TARGET_BRANCH")
    private String targetBranch;

    @Column(name = "C_CREATE_USER")
    private String createUser;

    @Column(name = "ARR_LABELS")
    @Type(type = "com.tuituidan.openhub.repository.convert.JpaArrayType")
    private String[] labels;

    @Column(name = "ARR_DEVELOPERS")
    @Type(type = "com.tuituidan.openhub.repository.convert.JpaArrayType")
    private String[] developers;

    @CreationTimestamp
    @Column(name = "DT_CREATE_TIME")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "DT_UPDATE_TIME")
    private LocalDateTime updateTime;
}
