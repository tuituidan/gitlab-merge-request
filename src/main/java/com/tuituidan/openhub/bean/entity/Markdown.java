package com.tuituidan.openhub.bean.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Markdown.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/27
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "T_MARKDOWN", schema = "DB_GITLAB")
public class Markdown implements Serializable {

    private static final long serialVersionUID = -208892079854187703L;
    @Id
    @Column(name = "N_ID")
    private Integer id;

    @Column(name = "C_MARKDOWN")
    private String markdown;

}
