package com.tuituidan.openhub.bean.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * DeveloperVo.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Getter
@Setter
@Accessors(chain = true)
public class DeveloperVo {

    private String id;

    private String name;

    private String loginId;

    private String avatarUrl;

    private Integer projectCount;

    private Integer mergeRequestCount;

    private Integer discussionCount;

    private Integer nodiscussionCount;
}
