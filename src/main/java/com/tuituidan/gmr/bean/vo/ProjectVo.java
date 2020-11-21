package com.tuituidan.gmr.bean.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Project.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
@Getter
@Setter
@Accessors(chain = true)
public class ProjectVo implements Serializable {

    private static final long serialVersionUID = 4488510442092781112L;

    private String id;

    private String gitlabUrl;

    private Integer projectId;

    private String projectName;

    private String projectDesc;

    private String[] developers;

    private String creator;

}
