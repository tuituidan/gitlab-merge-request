package com.tuituidan.gmr.util;

import com.tuituidan.gmr.exception.WrapperException;

import org.springframework.beans.BeanUtils;

/**
 * BeanExtUtils.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
public class BeanExtUtils {
    /**
     * 转换对象.
     *
     * @param source 源属性Dto
     * @param cls    目标属性Do
     * @param <T>    转换类型
     * @return T
     */
    public static <T> T convert(Object source, Class<T> cls) {
        return convert(source, cls, (String) null);
    }

    /**
     * 转换对象.
     *
     * @param source           源属性Dto
     * @param cls              目标属性Do
     * @param ignoreProperties 要忽略的属性
     * @param <T>              转换类型
     * @return T
     */
    public static <T> T convert(Object source, Class<T> cls, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        T target = newInstanceByCls(cls);
        BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }

    private static <T> T newInstanceByCls(Class<T> cls) {
        try {
            return cls.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new WrapperException("转换错误-{}", cls.getName(), ex);
        }
    }
}
