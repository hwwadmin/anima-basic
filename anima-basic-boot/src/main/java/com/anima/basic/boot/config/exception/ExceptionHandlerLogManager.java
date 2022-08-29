package com.anima.basic.boot.config.exception;

import com.anima.basic.common.SpringContextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 系统崩溃日志管理类
 *
 * @author hww
 */
@Component
public class ExceptionHandlerLogManager {

    @Value("${byfresh.log:defaultExceptionHandlerLog}")
    private String beanName;

    private ExceptionHandlerLog handlerLog;

    public ExceptionHandlerLog getHandlerLog() {
        if (Objects.isNull(this.handlerLog)) {
            refresh();
        }
        return this.handlerLog;
    }

    private synchronized void refresh() {
        if (Objects.nonNull(this.handlerLog)) {
            return;
        }
        this.handlerLog = SpringContextUtils.getBean(this.beanName);
    }

}
