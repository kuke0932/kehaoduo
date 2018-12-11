package com.htcs.kehaoduo.wechat.conf;

import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.common.util.CacheUtil;
import com.htcs.kehaoduo.common.util.SpringContextUtil;
import com.htcs.kehaoduo.system.bean.SysAgentAppid;
import com.htcs.kehaoduo.system.mapper.SysAgentAppidMapper;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

import java.io.Serializable;

/**
 * @author zhaokaiyuan
 * @create 2017-12-08 18:10
 **/

public class WxMpServices implements Serializable {

    private WxMpService wxMpService;

    private WxMpMenuService wxMpMenuService;

    private WxMpMessageRouter wxMpMessageRouter;

    public WxMpService getWxMpService() {
        return wxMpService;
    }

    public void setWxMpService(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    public WxMpMenuService getWxMpMenuService() {
        return wxMpMenuService;
    }

    public void setWxMpMenuService(WxMpMenuService wxMpMenuService) {
        this.wxMpMenuService = wxMpMenuService;
    }

    public WxMpMessageRouter getWxMpMessageRouter() {
        return wxMpMessageRouter;
    }

    public void setWxMpMessageRouter(WxMpMessageRouter wxMpMessageRouter) {
        this.wxMpMessageRouter = wxMpMessageRouter;
    }

    public static WxMpServices getInstance(String appId) {
        synchronized (WxMpServices.class) {
            WxMpServices wxMpServices  = (WxMpServices) CacheUtil.getInstance(CacheUtil.CacheType.MAP).get(appId);
            if(wxMpServices == null) {
                wxMpServices = new WxMpServices(appId);
                CacheUtil.getInstance(CacheUtil.CacheType.MAP).put(appId, wxMpServices);
            }
            return wxMpServices;
        }
    }

    private WxMpServices(String appId) {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        SysAgentAppidMapper sysAgentAppidMapper = SpringContextUtil.getBean(SysAgentAppidMapper.class);
        SysAgentAppid sysAgentAppid = sysAgentAppidMapper.selectByAppId(appId);
        Preconditions.checkNotNull(sysAgentAppid, "当前公众号无代理区域,请联系管理员");
        configStorage.setAppId(appId);
        configStorage.setSecret(sysAgentAppid.getAppSecret());
        configStorage.setAesKey(sysAgentAppid.getAesKey());
        configStorage.setToken(sysAgentAppid.getToken());

        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        this.setWxMpService(wxMpService);
    }

}
