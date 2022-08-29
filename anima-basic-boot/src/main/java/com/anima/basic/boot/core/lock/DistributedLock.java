package com.anima.basic.boot.core.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 *
 * @author hww
 */
public interface DistributedLock {

    boolean tryLock(String lockId, long keepMills);

    boolean tryLock(String lockId, long keepMills, long waitTime, TimeUnit waitTimeUnit);

    void unlock(String lockId);

}
