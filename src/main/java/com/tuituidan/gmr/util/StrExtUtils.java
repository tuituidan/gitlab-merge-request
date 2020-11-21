package com.tuituidan.gmr.util;

import java.util.UUID;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * StrExtUtils.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
@UtilityClass
@Slf4j
public class StrExtUtils {

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
