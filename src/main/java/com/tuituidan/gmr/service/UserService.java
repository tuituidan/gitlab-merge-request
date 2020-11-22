package com.tuituidan.gmr.service;

import com.tuituidan.gmr.bean.entity.User;
import com.tuituidan.gmr.bean.vo.UserVo;
import com.tuituidan.gmr.mybatis.mapper.UserMapper;
import com.tuituidan.gmr.util.BeanExtUtils;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * UserService.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Cacheable(value = "users")
    public UserVo getUserInfo(String id) {
        User user = userMapper.selectByPrimaryKey(id);
        return BeanExtUtils.convert(user, UserVo.class);
    }

}
