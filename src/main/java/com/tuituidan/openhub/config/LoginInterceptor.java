package com.tuituidan.openhub.config;

import com.tuituidan.openhub.bean.entity.Developer;
import com.tuituidan.openhub.consts.Consts;
import com.tuituidan.openhub.service.developer.DeveloperService;
import com.tuituidan.openhub.service.project.ProjectService;
import com.tuituidan.openhub.util.CookieUtils;
import com.tuituidan.openhub.util.RequestContextUtils;
import com.tuituidan.openhub.util.StringExtUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * LoginInterceptor.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Configuration
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private DeveloperService developerService;

    @Resource
    private ProjectService projectService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             @Nullable Object handler)
            throws Exception {
        String cookieVal = CookieUtils.getCookie(Consts.SESSIONID);
        if (StringUtils.isBlank(cookieVal)) {
            response.sendRedirect("/login");
            return false;
        }
        String userId = StringExtUtils.decodeBase64(cookieVal);
        Developer developer = developerService.getDeveloper(userId);
        RequestContextUtils.set(developer);
        request.setAttribute("userInfo", developerService.getDeveloperInfo(developer.getId()));
        request.setAttribute("projects", projectService.selectByUser(developer.getLoginId()));
        return true;
    }


}
