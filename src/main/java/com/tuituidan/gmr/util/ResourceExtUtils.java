package com.tuituidan.gmr.util;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 读取properties类型的配置，只需要在枚举中配置路径即可获取.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
public enum ResourceExtUtils {

    /**
     * MVC_VIEW.
     */
    MVC_VIEW("config/mvc-view.properties"),
    TEST("config/test.properties");

    private final Map<String, String> dataMap;

    @SneakyThrows
    ResourceExtUtils(String path) {
        Properties properties = PropertiesLoaderUtils
                .loadProperties(new EncodedResource(new ClassPathResource(path), StandardCharsets.UTF_8));
        dataMap = new HashMap<>(properties.size());
        for (Map.Entry<Object, Object> item : properties.entrySet()) {
            dataMap.put(item.getKey().toString(), item.getValue().toString());
        }
    }

    /**
     * getDataMap.
     *
     * @return Map
     */
    public Map<String, String> getDataMap() {
        return dataMap;
    }
}
