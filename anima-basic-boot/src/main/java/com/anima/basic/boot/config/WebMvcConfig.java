package com.anima.basic.boot.config;

import com.anima.basic.boot.config.interceptor.InterceptorInfo;
import com.anima.basic.boot.config.interceptor.InterceptorManager;
import com.anima.basic.boot.config.mvc.formatter.StringTrimFormatterFactory;
import com.anima.basic.boot.core.password.PasswordEncoder;
import com.anima.basic.boot.core.password.bcrypt.BCryptPasswordEncoder;
import com.anima.basic.boot.utils.JsonUtils;
import com.anima.basic.common.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * mvc配置器
 *
 * @author hww
 */
@Component
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private InterceptorManager interceptorManager;

    /**
     * 前端静态资源目录配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
        registry.addResourceHandler("/views/**").addResourceLocations("classpath:/views/");
    }

    /**
     * 设置跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").allowedOrigins("*").allowCredentials(true);
    }

    /**
     * 添加拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<InterceptorInfo> interceptorInfos = interceptorManager.getInterceptorInfos();
        if (CollectionUtils.isEmpty(interceptorInfos)) {
            return;
        }
        // 拦截器注册
        interceptorInfos.forEach(t -> {
            if (Objects.isNull(t.getClass())) {
                log.warn("[{}]拦截器配置缺失", t.getName());
                return;
            }
            HandlerInterceptor interceptor = SpringContextUtils.getBean(t.getInterceptor());
            InterceptorRegistration interceptorRegistration = registry.addInterceptor(interceptor);
            if (Objects.nonNull(t.getOrder())) {
                interceptorRegistration.order(t.getOrder());
            }
            if (CollectionUtils.isNotEmpty(t.getPathPatterns())) {
                interceptorRegistration.addPathPatterns(t.getPathPatterns());
            }
            if (CollectionUtils.isNotEmpty(t.getExcludePathPatterns())) {
                interceptorRegistration.excludePathPatterns(t.getExcludePathPatterns());
            }
            log.info("[{}]拦截器注册成功", t.getName());
        });
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new StringTrimFormatterFactory());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(JsonUtils.createObjectMapper());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
