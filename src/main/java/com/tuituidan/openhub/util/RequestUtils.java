package com.tuituidan.openhub.util;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * RequestUtils.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/12/26
 */
@UtilityClass
public class RequestUtils {

    /**
     * 转换前端表格分页参数.
     *
     * @return Pageable
     */
    public static Pageable getPageable() {
        HttpServletRequest request = getRequest();
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");
        String sortBy = request.getParameter("sortBy");
        if (StringUtils.isNotBlank(pageIndex)
                && StringUtils.isNotBlank(pageSize)
                && StringUtils.isNotBlank(sortBy)) {
            String direction = "asc";
            if (StringUtils.startsWith(sortBy, "-")) {
                direction = "desc";
                sortBy = StringUtils.substringAfter(sortBy, "-");
            }
            return PageRequest
                    .of(Integer.parseInt(pageIndex) - 1, Integer.parseInt(pageSize), Sort.Direction.fromString(direction),
                            sortBy);
        }
        return null;
    }

    /**
     * 得到reponse.
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attrs = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        return Optional.ofNullable(attrs).map(ServletRequestAttributes::getResponse)
                .orElseThrow(() -> new UnsupportedOperationException("请在web上下文使用"));
    }

    /**
     * 得到request.
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        return Optional.ofNullable(attrs).map(ServletRequestAttributes::getRequest)
                .orElseThrow(() -> new UnsupportedOperationException("请在web上下文使用"));
    }

}
