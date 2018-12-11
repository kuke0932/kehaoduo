package com.htcs.kehaoduo.wechat.conf;

import com.htcs.kehaoduo.wechat.handler.*;
import me.chanjar.weixin.mp.api.*;
import me.chanjar.weixin.mp.api.impl.WxMpMenuServiceImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.*;

/**
 * wechat mp configuration
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Configuration
@ConditionalOnClass(WxMpService.class)
@EnableConfigurationProperties(WechatMpProperties.class)
public class WechatMpConfiguration {
    @Autowired
    protected AbstractLogHandler logHandler;
    @Autowired
    protected AbstractNullHandler nullHandler;
    @Autowired
    protected AbstractKfSessionHandler kfSessionHandler;
    @Autowired
    protected AbstractStoreCheckNotifyHandler storeCheckNotifyHandler;
    @Autowired
    private WechatMpProperties properties;
    @Autowired
    private AbstractLocationHandler locationHandler;
    @Autowired
    private AbstractMenuHandler menuHandler;
    @Autowired
    private AbstractMsgHandler msgHandler;
    @Autowired
    private AbstractUnsubscribeHandler unsubscribeHandler;
    @Autowired
    private AbstractSubscribeHandler subscribeHandler;

    @Bean
    @ConditionalOnMissingBean
    public WxMpConfigStorage configStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(this.properties.getAppId());
        configStorage.setSecret(this.properties.getSecret());
        configStorage.setToken(this.properties.getToken());
        configStorage.setAesKey(this.properties.getAesKey());
        return configStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpService wxMpService(WxMpConfigStorage configStorage) {
//        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.okhttp.WxMpServiceImpl();
//        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.jodd.WxMpServiceImpl();
//        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.apache.WxMpServiceImpl();
        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        return wxMpService;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpMenuService wxMpMenuService (WxMpService wxMpService) {
        return new WxMpMenuServiceImpl(wxMpService);
    }

    @Bean
    public WxMpMessageRouter router(WxMpService wxMpService) {

        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
                .handler(this.kfSessionHandler).end();
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
                .handler(this.kfSessionHandler)
                .end();
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
                .handler(this.kfSessionHandler).end();

        // 门店审核事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.POI_CHECK_NOTIFY)
                .handler(this.storeCheckNotifyHandler).end();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(MenuButtonType.CLICK).handler(this.getMenuHandler()).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(MenuButtonType.VIEW).handler(this.nullHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.SUBSCRIBE).handler(this.getSubscribeHandler())
                .end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.UNSUBSCRIBE)
                .handler(this.getUnsubscribeHandler()).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.LOCATION).handler(this.getLocationHandler())
                .end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(XmlMsgType.LOCATION)
                .handler(this.getLocationHandler()).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.SCAN).handler(this.getScanHandler()).end();

        return newRouter;
    }


    public AbstractLogHandler getLogHandler() {
        return logHandler;
    }

    public void setLogHandler(AbstractLogHandler logHandler) {
        this.logHandler = logHandler;
    }

    public AbstractNullHandler getNullHandler() {
        return nullHandler;
    }

    public void setNullHandler(AbstractNullHandler nullHandler) {
        this.nullHandler = nullHandler;
    }

    public AbstractKfSessionHandler getKfSessionHandler() {
        return kfSessionHandler;
    }

    public void setKfSessionHandler(AbstractKfSessionHandler kfSessionHandler) {
        this.kfSessionHandler = kfSessionHandler;
    }

    public AbstractStoreCheckNotifyHandler getStoreCheckNotifyHandler() {
        return storeCheckNotifyHandler;
    }

    public void setStoreCheckNotifyHandler(AbstractStoreCheckNotifyHandler storeCheckNotifyHandler) {
        this.storeCheckNotifyHandler = storeCheckNotifyHandler;
    }

    public AbstractLocationHandler getLocationHandler() {
        return locationHandler;
    }

    public void setLocationHandler(AbstractLocationHandler locationHandler) {
        this.locationHandler = locationHandler;
    }

    public AbstractMenuHandler getMenuHandler() {
        return menuHandler;
    }

    public void setMenuHandler(AbstractMenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    public AbstractMsgHandler getMsgHandler() {
        return msgHandler;
    }

    public void setMsgHandler(AbstractMsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    public AbstractUnsubscribeHandler getUnsubscribeHandler() {
        return unsubscribeHandler;
    }

    public void setUnsubscribeHandler(AbstractUnsubscribeHandler unsubscribeHandler) {
        this.unsubscribeHandler = unsubscribeHandler;
    }

    public AbstractSubscribeHandler getSubscribeHandler() {
        return subscribeHandler;
    }

    public void setSubscribeHandler(AbstractSubscribeHandler subscribeHandler) {
        this.subscribeHandler = subscribeHandler;
    }

    protected AbstractHandler getScanHandler() {
        return null;
    }

}