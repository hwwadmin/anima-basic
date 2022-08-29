package com.anima.basic.boot.redis.sorted;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis字符型数据类型操作支持
 *
 * @author hww
 */
public class StringRedisSupport {

    private final RedisTemplate<String, Object> redisTemplate;

    public StringRedisSupport(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(final String key, final Object value, final long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public void set(final String key, final Object value, final long timeout, TimeUnit timeUtil) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUtil);
    }

    public Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public String getString(final String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public Integer getInteger(final String key) {
        return (Integer) redisTemplate.opsForValue().get(key);
    }

    public Long getLong(final String key) {
        return (Long) redisTemplate.opsForValue().get(key);
    }

    public List<Object> batchGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

}
