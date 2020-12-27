package com.tuituidan.openhub.util;

import com.tuituidan.openhub.bean.entity.Developer;

import lombok.experimental.UtilityClass;

/**
 * RequestContextUtils.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
@UtilityClass
public class RequestContextUtils {

    private static final ThreadLocal<Developer> LOCAL_LOGIN_INFO = new InheritableThreadLocal<>();
    static {
        LOCAL_LOGIN_INFO.remove();
    }

    public static Developer get() {
        return LOCAL_LOGIN_INFO.get();
    }

    public static void set(Developer developer) {
        LOCAL_LOGIN_INFO.remove();
        LOCAL_LOGIN_INFO.set(developer);
    }
}
