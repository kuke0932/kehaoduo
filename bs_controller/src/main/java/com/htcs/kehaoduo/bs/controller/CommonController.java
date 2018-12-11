package com.htcs.kehaoduo.bs.controller;

import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.wechat.conf.WxMpServices;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author zhaokaiyuan
 * @create 2017-11-20 11:59
 **/
@RequestMapping("/common")
@Controller
public class CommonController {

    Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    WxMpService wxMpService;

    @ResponseBody
    @PostMapping("/createSign")
    public ReturnStatus createSign(HttpServletRequest request) throws WxErrorException, UnsupportedEncodingException {
        String url = request.getParameter("url");
        String belongAppId = request.getParameter("belongAppId");

        WxMpService ws;
        if (StringUtils.isNotEmpty(belongAppId)) {
            ws = WxMpServices.getInstance(belongAppId).getWxMpService();
        } else {
            ws = wxMpService;
        }
        String decode = URLDecoder.decode(url, "utf-8");
        WxJsapiSignature jsapiSignature = ws.createJsapiSignature(decode);
        logger.debug("jsapi {}", jsapiSignature.toString());
        return new ReturnStatus(jsapiSignature);
    }

}
