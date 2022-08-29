package com.anima.basic.boot.core.redis.sorted;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * redis有序集合数据类型操作支持
 *
 * @author hww
 */
public class ZSetRedisSupport {

    private final RedisTemplate<String, Object> redisTemplate;

    public ZSetRedisSupport(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean add(final String key, final Object value, Integer score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public Integer score(final String key, final Object value) {
        Double score = redisTemplate.opsForZSet().score(key, value);
        return score == null ? null : score.intValue();
    }

    public Set<ZSetOperations.TypedTuple<Object>> getAll(final String key) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, 0, Integer.MAX_VALUE);
    }

    public void incr(final String key, final Object value) {
        redisTemplate.opsForZSet().incrementScore(key, value, 1);
    }

}
