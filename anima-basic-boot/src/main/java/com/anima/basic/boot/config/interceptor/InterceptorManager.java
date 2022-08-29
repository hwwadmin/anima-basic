package com.anima.basic.boot.config.interceptor;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 拦截器注册管理
 *
 * @author hww
 */
@Component
public class InterceptorManager {

    @Autowired(required = false)
    private Map<String, InterceptorRegister> interceptorRegisterMap;

    @Getter
    private final List<InterceptorInfo> interceptorInfos = Lists.newArrayList();

    @PostConstruct
    public void initialized() {
        if (Objects.isNull(interceptorRegisterMap)) {
            return;
        }
        for (InterceptorRegister register : interceptorRegisterMap.values()) {
            interceptorInfos.addAll(register.getInterceptorInfos());
        }
    }

}
