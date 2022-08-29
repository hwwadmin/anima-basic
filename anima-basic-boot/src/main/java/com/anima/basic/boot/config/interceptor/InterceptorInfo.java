package com.anima.basic.boot.config.interceptor;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

/**
 * 拦截器信息
 *
 * @author hww
 */
@Data
@Builder
public class InterceptorInfo {

    private String name;

    private Class<? extends HandlerInterceptor> interceptor;

    private Integer order;

    private List<String> pathPatterns;

    private List<String> excludePathPatterns;

}
