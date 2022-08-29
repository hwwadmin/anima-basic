package com.anima.basic.framework.crashLog.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.anima.basic.boot.core.redis.RedisSupport;
import com.anima.basic.framework.AnimaFrameworkConstants;
import com.anima.basic.framework.crashLog.dao.CrashLogDao;
import com.anima.basic.framework.crashLog.model.CrashLogEntity;
import com.anima.basic.framework.crashLog.service.CrashLogDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * 崩溃日志领域服务实现
 *
 * @author hww
 */
@Service
public class CrashLogDomainServiceImpl implements CrashLogDomainService {

    @Autowired
    private CrashLogDao crashLogDao;
    @Autowired
    private RedisSupport redisSupport;

    @Override
    public void putLog(String operation, String message, Throwable e) {
        CrashLogEntity logEntity = new CrashLogEntity();
        logEntity.setOperation(operation);
        logEntity.setMessage(message);
        logEntity.setStackException(ExceptionUtil.stacktraceToString(e));
        logEntity.setCreateTime(Instant.now()); // 设置创建时间，不然是redis取出来后由切面统一设置是不准的
        this.redisSupport.getOps4list().leftPush(AnimaFrameworkConstants.crashLog_cache, logEntity);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public void saveAll(List<CrashLogEntity> entities) {
        this.crashLogDao.saveAll(entities);
    }

}
