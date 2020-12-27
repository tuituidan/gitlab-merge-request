package com.tuituidan.openhub.bean.vo;

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
public class ProjectVo {

    private Integer id;

    private String name;

    private String nameWithSpace;

    private String desc;

    private String createUser;

    private String targetBranch;

    private String[] labels;

    private String[] developers;

    private String[] developerNames;

}
