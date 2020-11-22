package com.tuituidan.gmr.util;

import java.util.UUID;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

/**
 * StrExtUtils.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/21
 */
@UtilityClass
@Slf4j
public class StringExtUtils {

    /**
     * getUuid.
     *
     * @return String
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 使用 Slf4j 中的字符串格式化方式来格式化字符串.
     *
     * @param pattern 待格式化的字符串
     * @param args 参数
     * @return 格式化后的字符串
     */
    public static String format(String pattern, Object... args) {
        return pattern == null ? "" : MessageFormatter.arrayFormat(pattern, args).getMessage();
    }

}
