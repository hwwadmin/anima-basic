package com.anima.basic.boot.core.redis.sorted;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * redis特殊操作支持
 *
 * @author hww
 */
@Slf4j
public class SpecialOpsRedisSupport {

    private final RedisTemplate<String, Object> redisTemplate;

    public SpecialOpsRedisSupport(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 模糊匹配前缀删除
     */
    public void delByPre(String pre) {
        if (StringUtils.isEmpty(pre)) {
            return;
        }
        Set<String> keys = keys(pre + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 模糊匹配
     */
    public Map<String, Integer> findByPre(String prefix) {
        if (StringUtils.isEmpty(prefix)) {
            return null;
        }
        Map<String, Integer> ret = new HashMap<>();
        Set<String> keys = keys(prefix + "*");
        assert keys != null;
        for (String key : keys) {
            Object v = redisTemplate.opsForValue().get(key);
            ret.put(key, (Integer) v);
        }
        return ret;
    }

    /**
     * 获取符合条件的key
     * redis keys 方法数据量大的时候 会阻塞redis线程
     * 使用 scan 替代
     *
     * @param pattern 表达式
     * @return
     */
    public Set<String> keys(String pattern) {
        Set<String> keys = new HashSet<>();
        this.scan(pattern, item -> {
            //符合条件的key
            String key = new String(item, StandardCharsets.UTF_8);
            keys.add(key);
        });
        return keys;
    }

    /**
     * scan 实现
     *
     * @param pattern  表达式
     * @param consumer 对迭代到的key进行操作
     */
    public void scan(String pattern, Consumer<byte[]> consumer) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(10000L).match(pattern).build());
            cursor.forEachRemaining(consumer);
            return null;
        });
    }

    /**
     * 记录访问评率
     * 基于redis的限流控制
     */
    public boolean limitRate(String key, int time, int cnt) {
        key = "rateLimit_" + key;
        Long size = redisTemplate.opsForList().size(key);
        log.info("key = {}, limitRate cnt = {}", key, size);
        Integer index = cnt - 1;
        if (index < 0) {
            index = 0;
        }
        if (size < cnt) {
            redisTemplate.opsForList().leftPush(key, System.currentTimeMillis());
        } else {
            //取最后一个时间
            Long lastTime = (Long) redisTemplate.opsForList().index(key, -1);
            Long currentTime = System.currentTimeMillis();
            log.info("key ={},lastTime = {},currentTime = {}", key, lastTime, currentTime);
            if ((currentTime - lastTime) < time * 1000) {
                return true;
            } else {
                redisTemplate.opsForList().leftPush(key, System.currentTimeMillis());
                //保留 cnt -1 个数据
                redisTemplate.opsForList().trim(key, 0, index);
            }
        }
        return false;
    }

    /**
     * 初始化自增长值
     */
    public void setIncr(String key, Long value) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.set(value);
    }

    /**
     * 获取自增长值
     */
    public Long getIncr(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return entityIdCounter.getAndIncrement();
    }

    /**
     * Redis原子自增操作, 更适合一次性使用的场景
     */
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * Redis原子自增操作, 更适合一次性使用的场景
     */
    public Long incr(String key, final long timeout, final TimeUnit unit) {
        Long result = redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, timeout, unit);
        return result;
    }

    /**
     * Redis原子自增操作, 更适合一次性使用的场景
     *
     * @param key   key
     * @param delta 步长
     * @return 自增后的值
     */
    public Long incr(String key, Long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

}
