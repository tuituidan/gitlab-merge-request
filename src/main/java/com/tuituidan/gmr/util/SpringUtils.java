package com.tuituidan.gmr.util;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringUtils.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 设置ApplicationContext
     *
     * @param applicationContext ApplicationContext
     */
    private static void setContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * Gets bean.
     *
     * @param <T>   the type parameter
     * @param name  the name
     * @param clazz the clazz
     * @return the bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return SpringUtils.applicationContext.getBean(name, clazz);
    }

    /**
     * Gets bean.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return SpringUtils.applicationContext.getBean(clazz);
    }

    /**
     * Get beans map.
     *
     * @param <T>   T
     * @param clazz the clazz
     * @return map
     */
    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        return SpringUtils.applicationContext.getBeansOfType(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.setContext(applicationContext);
        }
    }
}
