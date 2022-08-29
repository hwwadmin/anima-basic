package com.anima.basic.framework.crashLog;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.anima.basic.boot.config.exception.DefaultExceptionHandlerLog;
import com.anima.basic.common.exception.IllegalValidatedException;
import com.anima.basic.framework.crashLog.service.CrashLogDomainService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 基于CrashLog的系统崩溃日志实现
 *
 * @author hww
 */
@Slf4j
@Component("crashLog")
public class CrashLogExceptionHandler extends DefaultExceptionHandlerLog {

    @Resource
    private CrashLogDomainService crashLogDomainService;

    /**
     * 忽略的异常类型
     */
    private static final Set<Class<? extends Exception>> ignore = ImmutableSet.<Class<? extends Exception>>builder()
            .add(IllegalValidatedException.class)
            .add(IllegalArgumentException.class)
            .add(MethodArgumentNotValidException.class)
            .build();

    private static final Cache<String, String> exCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .build();

    @Override
    public void log(HttpServletRequest request, Throwable ex) {
        StackTraceElement traceElement = ex.getStackTrace()[ex.getStackTrace().length - 1];
        String key = traceElement.getFileName() + ":" +
                traceElement.getClassName() + ":" +
                traceElement.getMethodName() + ":" +
                traceElement.getLineNumber();
        if (exCache.getIfPresent(key) != null) {
            return;
        }
        exCache.put(key, "");
        for (Class<? extends Exception> clazz : ignore) {
            if (ExceptionUtil.isCausedBy(ex, clazz)) {
                return;
            }
        }
        // 打印下日志
        super.log(request, ex);
        // 保存日志
        String msg = ExceptionUtil.getSimpleMessage(ex);
        if (msg.length() > 255) {
            // 太长的异常消息就截断直接看堆栈
            msg = msg.substring(0, 254);
        }
        this.crashLogDomainService.putLog(request.getRequestURI(), msg, ex);
    }

}
