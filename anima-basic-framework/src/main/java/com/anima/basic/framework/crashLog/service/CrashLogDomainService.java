package com.anima.basic.framework.crashLog.service;


import com.anima.basic.boot.core.mvc.CrudService;
import com.anima.basic.framework.crashLog.model.CrashLogEntity;

/**
 * 崩溃日志领域服务
 *
 * @author hww
 */
public interface CrashLogDomainService extends CrudService<CrashLogEntity> {

    void putLog(String operation, String message, Throwable e);

}
