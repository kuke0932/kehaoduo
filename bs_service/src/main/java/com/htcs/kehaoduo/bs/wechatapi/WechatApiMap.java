package com.htcs.kehaoduo.bs.wechatapi;

import com.htcs.kehaoduo.bs.wechatapi.api.WechatApi;
import com.htcs.kehaoduo.common.util.CacheUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaokaiyuan
 * @create 2017-10-31 15:09
 **/
public class WechatApiMap {

    public static WechatApi get(String key) {
        return (WechatApi) CacheUtil.getInstance(CacheUtil.CacheType.EHCACHE).get(key);
    }

    public static void put(String key, WechatApi wechatApi) {
        CacheUtil.getInstance(CacheUtil.CacheType.EHCACHE).put(key, wechatApi, 600, TimeUnit.SECONDS);
    }

    public static void remove(String key) {
        CacheUtil.getInstance(CacheUtil.CacheType.EHCACHE).remove(key);
    }

}
