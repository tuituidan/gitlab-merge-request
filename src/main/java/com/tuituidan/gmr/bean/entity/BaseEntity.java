package com.tuituidan.gmr.bean.entity;

import java.time.LocalDateTime;

/**
 * BaseEntity.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
public interface BaseEntity<T> {

    /**
     * getId.
     *
     * @return String
     */
    String getId();

    /**
     * setId.
     *
     * @param id id
     * @return T
     */
    T setId(String id);

    T setCreateTime(LocalDateTime createTime);

    LocalDateTime getCreateTime();


    T setUpdateTime(LocalDateTime updateTime);


    LocalDateTime getUpdateTime();

}
