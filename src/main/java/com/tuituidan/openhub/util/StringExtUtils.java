package com.tuituidan.openhub.util;

import com.tuituidan.openhub.exception.WrapperException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.Base64Utils;

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
     * 使用 Slf4j 中的字符串格式化方式来格式化字符串.
     *
     * @param pattern 待格式化的字符串
     * @param args    参数
     * @return 格式化后的字符串
     */
    public static String format(String pattern, Object... args) {
        return pattern == null ? "" : MessageFormatter.arrayFormat(pattern, args).getMessage();
    }

    /**
     * base64加密.
     *
     * @param source 原字符串
     * @return 加密后的字符串
     */
    public static String encodeBase64(String source) {
        return Base64Utils.encodeToString(source.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64解密.
     *
     * @param source 原字符串
     * @return 解密后的字符串
     */
    public static String decodeBase64(String source) {
        try {
            return IOUtils.toString(Base64Utils.decodeFromString(source),
                    StandardCharsets.UTF_8.name());
        } catch (IOException ex) {
            throw new WrapperException("解密base64失败，原字符串-【{}】", source, ex);
        }
    }
}
