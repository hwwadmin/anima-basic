package com.anima.basic.boot.config.exception;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统崩溃日志处理接口
 *
 * @author hww
 */
public interface ExceptionHandlerLog {

    void log(HttpServletRequest request, Throwable ex);

}
