package com.htcs.kehaoduo.common.util;

import java.util.concurrent.TimeUnit;

public class CacheUtil {

    public static CustomCache getInstance(CacheType type) {
        switch (type) {
            case REDIS:
                return SpringContextUtil.getApplicationContext().getBean("redisCacheUtil", RedisCacheUtil.class);
            case EHCACHE:
                return EhcacheUtil.getInstance();
            case MAP:
                return MapCacheUtil.getInstance();
            default:
                return null;
        }
    }

    /**
     *
     */
    public enum CacheType {
        REDIS,
        EHCACHE,
        MAP
    }

    public interface CustomCache {
        public void put(String cacheName, String key, Object value);

        public void put(String cacheName, String key, Object value, long val, TimeUnit timeUnit);

        public void put(String key, Object value);

        public void put(String key, Object value, long val, TimeUnit timeUnit);


        public Object get(String cacheName, String key);

        public Object get(String key);

        public void remove(String cacheName, String key);

        public void remove(String key);

    }
}