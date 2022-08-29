package com.anima.basic.boot.redis.sorted;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * redis集合数据类型操作支持
 *
 * @author hww
 */
public class SetRedisSupport {

    private final RedisTemplate<String, Object> redisTemplate;

    public SetRedisSupport(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public long set(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    public Set<Object> get(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public long del(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }

    public long size(final String key) {
        Long size = redisTemplate.opsForSet().size(key);
        return size == null ? 0L : size;
    }

}
