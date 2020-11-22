package com.tuituidan.gmr.config;

import com.tuituidan.gmr.util.ResourceExtUtils;

import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebAppConfig.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Configuration
@Slf4j
public class WebAppConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        for (Map.Entry<String, String> item : ResourceExtUtils.MVC_VIEW.getDataMap().entrySet()) {
            registry.addViewController(item.getKey().replace(".", "/"))
                    .setViewName(item.getValue());
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/assets/**", "/favicon.ico", "/error/**");
    }


}
