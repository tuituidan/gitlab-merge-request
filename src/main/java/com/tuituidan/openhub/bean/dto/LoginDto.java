package com.tuituidan.openhub.bean.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 当前登录信息.
 *
 * @author zhujunhan
 * @version 1.0
 * @date 2020/11/26
 */
@Getter
@Setter
@Accessors(chain = true)
public class LoginDto {

    private String id;

    private String loginId;

    private String password;
}
