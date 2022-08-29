package com.anima.basic.boot.config.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.anima.basic.common.exception.IllegalValidatedException;
import com.anima.basic.common.exception.UtilsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认的系统崩溃日志处理
 * 简单打印下文件日志
 */
@Slf4j
@Component("defaultExceptionHandlerLog")
public class DefaultExceptionHandlerLog implements ExceptionHandlerLog {

    @Override
    public void log(HttpServletRequest request, Throwable ex) {
        String sessionId = WebUtils.getSessionId(request);
        String path = request.getRequestURI();
        log.info("错误信息：sessionId:{} - path:{} - ex:{}", sessionId, path, ex.getClass().getName());
        if ((ex instanceof IllegalArgumentException) || (ex instanceof UtilsException)
                || (ex instanceof IllegalValidatedException)) {
            // 简化日志输出
            log.error("全局异常：{}", ExceptionUtil.getSimpleMessage(ex));
        } else {
            log.error("全局异常", ex);
        }
    }

}
