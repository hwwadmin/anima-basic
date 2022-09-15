package com.anima.basic.boot.config.mvc;

import com.anima.basic.boot.config.exception.GlobalExceptionHandler;
import com.anima.basic.boot.core.pojo.vo.ListVo;
import com.anima.basic.common.enums.StatusCodeEnum;
import com.anima.basic.common.result.Response;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collection;

/**
 * 统一的返回体封装
 */
@ControllerAdvice(basePackages = "com.anima")
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> declaringClass;
        try {
            declaringClass = returnType.getMethod().getDeclaringClass();
        } catch (Exception e) {
            return false;
        }
        if (declaringClass == GlobalExceptionHandler.class) return false; // 异常处理结果不统一封装
        return returnType.getMethod().getReturnType() != Response.class;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        if (body instanceof String) {
            return Response.fail(StatusCodeEnum.commonError, "返回体不符合规范");
        }
        if (body instanceof Collection<?>) {
            return Response.success(new ListVo<>(body));
        }
        return Response.success(body);
    }

}
