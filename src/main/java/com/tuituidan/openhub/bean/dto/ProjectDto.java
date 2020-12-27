package com.tuituidan.openhub.bean.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ProjectDto.
 *
 * @author zhujunhan
 * @version 1.0
 * @date 2020/11/23
 */
@Getter
@Setter
public class ProjectDto {

    private Integer id;

    private String[] developers;

    private String targetBranch;

    private String[] labels;
}
