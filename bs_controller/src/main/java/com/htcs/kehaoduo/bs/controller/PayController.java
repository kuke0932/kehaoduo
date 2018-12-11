package com.htcs.kehaoduo.bs.controller;

import com.github.binarywang.wxpay.bean.request.WxEntPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayBaseRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxEntPayResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.common.conf.RequestUserHolder;
import com.htcs.kehaoduo.common.model.LoginWebUser;
import com.htcs.kehaoduo.common.util.RandomStrUtils;
import com.htcs.kehaoduo.common.util.UUIDUtil;
import com.htcs.kehaoduo.common.util.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaokaiyuan
 * @create 2017-11-16 9:40
 **/
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    WxPayService wxPayService;
    @Value("${wechat.pay.notifyUrl}")
    private String notifyUrl;

    @PostMapping("/createSign")
    @ResponseBody
    public ReturnStatus createSign1(String openid, String fee) throws WxPayException {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        String orderNo = UUIDUtil.getUUID();

        WxPayConfig wxPayConfig = wxPayService.getConfig();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        WxPayUnifiedOrderRequest build = WxPayUnifiedOrderRequest.newBuilder()
                .attach(loginWebUser.getWebUserId().toString())
                .totalFee(WxPayBaseRequest.yuanToFee(fee))
                .subOpenid(openid)
                .outTradeNo(orderNo)
                .tradeType("JSAPI")
                .notifyURL(notifyUrl)
                .spbillCreateIp(WebUtils.getIpAddr(requestAttributes.getRequest()))
                .body("客好多充值").build();
        build.setAppid(wxPayConfig.getAppId());
        build.setSubAppId(wxPayConfig.getSubAppId());
        build.setMchId(wxPayConfig.getMchId());
        build.setSubMchId(wxPayConfig.getSubMchId());
        WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(build);
        String prepayId = wxPayUnifiedOrderResult.getPrepayId();

        long timeStamp = System.currentTimeMillis();
        String nonceStr = RandomStrUtils.generateStr(16);
        String signType = "MD5";
        String _package = "prepay_id=" + prepayId;
        Map<String, String> params = new HashMap<>();

        params.put("appId", wxPayConfig.getSubAppId());
        params.put("timeStamp", String.valueOf(timeStamp / 1000));
        params.put("nonceStr", nonceStr);
        params.put("signType", signType);
        params.put("package", _package);

        String sign = SignUtils.createSign(params, WxPayConstants.SignType.MD5, wxPayConfig.getMchKey(), false);

        params.put("paySign", sign);

        return new ReturnStatus(params);
    }

    @PostMapping("/withdraw")
    @ResponseBody
    public ReturnStatus withdraw(String openid, String fee, String password) throws WxPayException {


        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        String s = DigestUtils.md5Hex(loginWebUser.getSalt() + password);
        Preconditions.checkArgument(s.equalsIgnoreCase(loginWebUser.getLoginPwd()), "用户名不正确，请重新输入！");
        WxPayConfig wxPayConfig = wxPayService.getConfig();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String orderNo = UUIDUtil.getUUID();

        WxEntPayRequest build = WxEntPayRequest.newBuilder()
                .mchAppid(wxPayConfig.getSubAppId())
                .mchId(wxPayConfig.getSubMchId())
                .partnerTradeNo(orderNo)
                .openid(openid)
                .checkName("NO_CHECK")
                .amount(WxPayBaseRequest.yuanToFee(fee))
                .description("提现")
                .spbillCreateIp(WebUtils.getIpAddr(requestAttributes.getRequest()))
                .build();

        WxEntPayResult wxEntPayResult = wxPayService.entPay(build);

        return new ReturnStatus();
    }
}
