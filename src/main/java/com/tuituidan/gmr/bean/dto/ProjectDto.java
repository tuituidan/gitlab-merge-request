package com.tuituidan.gmr.bean.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ProjectDto.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Getter
@Setter
public class ProjectDto {
    private String id;

    private Integer projectId;

    private String loginId;

    private String password;

    private String privateToken;

    private String[] developers;
}
