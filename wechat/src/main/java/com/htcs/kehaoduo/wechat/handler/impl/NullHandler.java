package com.htcs.kehaoduo.wechat.handler.impl;

import com.htcs.kehaoduo.wechat.handler.AbstractNullHandler;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhaokaiyuan on 17-7-10.
 */
@Component
public class NullHandler extends AbstractNullHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxMpService wxCpService, WxSessionManager wxSessionManager) throws WxErrorException {
        return null;
    }
}
