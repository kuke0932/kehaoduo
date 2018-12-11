package com.htcs.kehaoduo.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author htcs
 */
@Component
public class RedisCacheUtil implements CacheUtil.CustomCache {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void put(String cacheName, String key, Object value) {

        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);

    }

    @Override
    public void put(String cacheName, String key, Object value, long val, TimeUnit timeUnit) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, val, timeUnit);
    }

    @Override
    public void put(String key, Object value) {
        this.put(null, key, value);
    }

    @Override
    public void put(String key, Object value, long val, TimeUnit timeUnit) {
        this.put(null, key, value, val, timeUnit);
    }

    @Override
    public Object get(String cacheName, String key) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public Object get(String key) {
        return this.get(null, key);
    }

    @Override
    public void remove(String cacheName, String key) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        redisTemplate.delete(key);
    }

    @Override
    public void remove(String key) {
        this.remove(null, key);
    }
}
