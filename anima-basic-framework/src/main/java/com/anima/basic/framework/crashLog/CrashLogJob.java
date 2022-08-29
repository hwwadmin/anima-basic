package com.anima.basic.framework.crashLog;

import com.anima.basic.boot.core.lock.DistributedLock;
import com.anima.basic.boot.core.redis.RedisSupport;
import com.anima.basic.framework.AnimaFrameworkConstants;
import com.anima.basic.framework.crashLog.model.CrashLogEntity;
import com.anima.basic.framework.crashLog.service.CrashLogDomainService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * CrashLog日志定时任务
 *
 * @author hww
 * @date 2022/8/9
 */
@Component
@Slf4j
public class CrashLogJob {

    @Autowired
    private CrashLogDomainService crashLogDomainService;
    @Autowired
    private DistributedLock lock;
    @Autowired
    private RedisSupport redisSupport;

    @Scheduled(cron = "*/10 * * * * ?")
    protected void doTask() {
        try {
            if (lock.tryLock(AnimaFrameworkConstants.crashLog_lock, 1000)) {
                List<CrashLogEntity> entities = Lists.newArrayList();
                while (true) {
                    CrashLogEntity entity = (CrashLogEntity) this.redisSupport.getOps4list().rightPop(AnimaFrameworkConstants.crashLog_cache);
                    if (Objects.isNull(entity)) {
                        break;
                    }
                    entities.add(entity);
                }
                this.crashLogDomainService.batchSave(entities);
            }
        } catch (Exception e) {
            log.error("CrashLog日志定时任务异常", e);
        } finally {
            lock.unlock(AnimaFrameworkConstants.crashLog_lock);
        }
    }

}
