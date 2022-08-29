package com.anima.basic.boot.lock;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 基于redis的分布式锁
 *
 * @author hww
 */
@Component
public class RedisDistributedLock implements DistributedLock {

    private static final String lockSpace = "lock";

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean tryLock(String lockId, long keepMills) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockId, lockSpace, keepMills, TimeUnit.MILLISECONDS);
        return Objects.nonNull(success) && success;
    }

    @Override
    public boolean tryLock(String lockId, long keepMills, long waitTime, TimeUnit waitTimeUnit) {
        return SpinBarrier.spin(() -> tryLock(lockId, keepMills), waitTimeUnit.toMillis(waitTime));
    }

    @Override
    public void unlock(String lockId) {
        redisTemplate.delete(lockId);
    }

}
