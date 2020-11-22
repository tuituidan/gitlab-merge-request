package com.tuituidan.gmr.bean.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * DeveloperVo.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Getter
@Setter
public class DeveloperVo {

    private String id;

    private String name;

    private String avatarUrl;

    private String projectCount;

    private String mergeRequestCount;

    private String discussionCount;

    private String nodiscussionCount;
}
