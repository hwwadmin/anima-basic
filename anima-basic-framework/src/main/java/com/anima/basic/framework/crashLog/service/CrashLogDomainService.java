package com.anima.basic.framework.crashLog.service;


import com.anima.basic.framework.crashLog.model.CrashLogEntity;

import java.util.List;

/**
 * 崩溃日志领域服务
 *
 * @author hww
 */
public interface CrashLogDomainService {

    void putLog(String operation, String message, Throwable e);

    void saveAll(List<CrashLogEntity> entities);

}
