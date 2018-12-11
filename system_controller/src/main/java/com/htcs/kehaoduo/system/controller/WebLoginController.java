package com.htcs.kehaoduo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.htcs.kehaoduo.common.CacheConstans;
import com.htcs.kehaoduo.common.Common;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.common.conf.RequestUserHolder;
import com.htcs.kehaoduo.common.exception.ReturnStatusException;
import com.htcs.kehaoduo.common.model.LoginWebUser;
import com.htcs.kehaoduo.common.util.CacheUtil;
import com.htcs.kehaoduo.common.util.MD5Util;
import com.htcs.kehaoduo.common.util.SmsUtil;
import com.htcs.kehaoduo.common.util.token.JWTokenUtil;
import com.htcs.kehaoduo.system.bean.WebUser;
import com.htcs.kehaoduo.system.service.WebUserService;
import com.htcs.kehaoduo.wechat.conf.WxMpServices;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;


@RestController
@RequestMapping("/web")
/**
 * @author zhaokaiyuan
 */
public class WebLoginController {

    private static Logger logger = LoggerFactory.getLogger(WebLoginController.class);
    @Autowired
    WxMpService wxMpService;
    @Autowired
    private WebUserService webUserService;

    @Value("${site}")
    private String site;
    /**
     * 自动登录
     *
     * @param code
     * @return
     * @throws WxErrorException
     */
    @GetMapping("/autoLogin")
    public ReturnStatus autoLogin(String code, String belongAppId, @RequestParam(defaultValue = "A") String fromWhich) throws WxErrorException {
        JSONObject jsonObject = new JSONObject();
        WxMpServices instance = WxMpServices.getInstance(belongAppId);

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = instance.getWxMpService().oauth2getAccessToken(code);
        WxMpUser wxMpUser = instance.getWxMpService().oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");

        jsonObject.put("unionid", wxMpUser.getUnionId());
        jsonObject.put("openid" + fromWhich, wxMpUser.getOpenId());
        jsonObject.put("nickName", wxMpUser.getNickname());
        jsonObject.put("headImgUrl", wxMpUser.getHeadImgUrl());

        WebUser webUser;
        if (fromWhich.equals("A")) {
            webUser = webUserService.getWebUserByOpenidA(wxMpUser.getOpenId());
        } else {
            webUser = webUserService.getWebUserByOpenidB(wxMpUser.getOpenId());
        }
        if (webUser != null) {
            String userKey = webUser.getLoginName() + ".web";
            String token = JWTokenUtil.createToken(userKey);

            LoginWebUser loginWebUser = webUser.copyOf();
            CacheUtil.getInstance(CacheUtil.CacheType.REDIS).put(CacheConstans.SYSTEM_CACHE, userKey, loginWebUser);
            jsonObject.put("token", token);
            jsonObject.put("userId", webUser.getId());
            jsonObject.put("lastLoginType", webUser.getLastLoginType());
            jsonObject.put("site", site);
            return new ReturnStatus(ReturnStatus.StatusCode.OK, "", jsonObject);
        } else {
            return new ReturnStatus(ReturnStatus.StatusCode.UNAUTHORIZED, "尚未找到登录信息，请手动登录", jsonObject);
        }

    }

    /**
     * 登录
     *
     * @param mobile
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ReturnStatus login(String mobile, String password, HttpServletRequest request) {

        Preconditions.checkArgument(StringUtils.isNotEmpty(mobile), "手机号不能为空，请重新输入！");
        Preconditions.checkArgument(StringUtils.isNotEmpty(password), "密码不能为空，请重新输入！");
        WebUser webUserByMobile = webUserService.getWebUserByMobile(mobile);
        WebUser webUser = Preconditions.checkNotNull(webUserByMobile, "用户不存在，请注册！");
        String correctPwd = webUser.getLoginPwd();
        String salt = webUser.getSalt();
        String inputPwd = MD5Util.MD5Encode(salt + password);
        Preconditions.checkArgument(java.util.Objects.equals(correctPwd, inputPwd), "用户名或者密码不对，请重新输入！");

        webUserHasChecked(webUser);

        String nickName = request.getParameter("nickName");
        String headImgUrl = request.getParameter("headImgUrl");
        String openidA = request.getParameter("openidA");
        String openidB = request.getParameter("openidB");
        String unionid = request.getParameter("unionid");

        WebUser webUser1 = new WebUser();
        webUser1.setId(webUser.getId());
        webUser1.setWebUserId(webUser.getId());
        if (StringUtils.isNotEmpty(openidA)) {
            webUser1.setOpenidA(openidA);
        }
        if (StringUtils.isNotEmpty(openidB)) {
            webUser1.setOpenidB(openidB);
        }
        if (StringUtils.isNotEmpty(nickName)) {
            webUser1.setUserName(nickName);
        }
        if (StringUtils.isNotEmpty(headImgUrl)) {
            webUser1.setHeadImg(headImgUrl);
        }
        if (StringUtils.isNotEmpty(unionid)) {
            webUser1.setUnionid(unionid);
        }
        webUser1.setLastLoginTime(new Date());
        webUserService.updateWebUser(webUser1);

        String userKey = webUser.getLoginName() + ".web";
        String token = JWTokenUtil.createToken(userKey);

        LoginWebUser loginWebUser = webUser.copyOf();
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).put(CacheConstans.SYSTEM_CACHE, userKey, loginWebUser);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", webUser.getId());
        jsonObject.put("token", token);
        jsonObject.put("site", site);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "", jsonObject);
    }

    /**
     * 注销
     *
     * @param id
     * @return
     */
    @PostMapping("/logout")
    public ReturnStatus logout(String id) {

        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();

        WebUser webUser = new WebUser();
        webUser.setId(loginWebUser.getId());
        webUser.setWebUserId(loginWebUser.getId());
        webUser.setOpenidA("");
        webUserService.updateWebUser(webUser);
        String userKey = loginWebUser.getLoginName() + ".web";

        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).remove(CacheConstans.SYSTEM_CACHE, userKey);

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "");

    }

    /**
     * 注册并登录
     *
     * @param mobile
     * @param verifyCode
     * @param password
     * @return
     */
    @PostMapping("/register")
    public ReturnStatus register(String mobile, String verifyCode, String password, String lastLoginType, HttpServletRequest request) {

        //是否已经注册
        boolean isRegistered = webUserService.isRegistered(mobile);
        Preconditions.checkArgument(!isRegistered, "手机号已注册，请登录！");

        //验证码是否正确
        String verifyCodeCache = (String) CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(mobile + Common.VerifyCodeType.REGISTER);
        Preconditions.checkNotNull(verifyCodeCache, "验证码已过期，请重新获取！");
        boolean codeValid = java.util.Objects.equals(verifyCode, verifyCodeCache);
        Preconditions.checkArgument(codeValid, "验证码不正确，请重新输入或重新获取！");


        String nickName = request.getParameter("nickName");
        String headImgUrl = request.getParameter("headImgUrl");
        String openidA = request.getParameter("openidA");
        String unionid = request.getParameter("unionid");
        String belongAppId = request.getParameter("belongAppId");
        String checkStatus = request.getParameter("checkStatus");

        WebUser webUser = new WebUser();
        webUser.setMobile(mobile);
        webUser.setLoginPwd(password);
        webUser.setLoginName(mobile);
        webUser.setCheckStatus(Integer.valueOf(checkStatus));
        webUser.setLastLoginType(lastLoginType);
        if (StringUtils.isNotEmpty(nickName)) {
            webUser.setUserName(nickName);
        }
        if (StringUtils.isNotEmpty(headImgUrl)) {
            webUser.setHeadImg(headImgUrl);
        }
        if (StringUtils.isNotEmpty(openidA)) {
            webUser.setOpenidA(openidA);
        }
        if (StringUtils.isNotEmpty(unionid)) {
            webUser.setUnionid(unionid);
        }
        if (StringUtils.isNotEmpty(belongAppId)) {
            webUser.setBelongAppId(belongAppId);
        }
        webUser.setLastLoginTime(new Date());
        webUserService.addWebUser(webUser);


        String userKey = webUser.getLoginName() + ".web";
        String token = JWTokenUtil.createToken(userKey);

        LoginWebUser loginWebUser = webUser.copyOf();
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).put(CacheConstans.SYSTEM_CACHE, userKey, loginWebUser);

        //验证通过之后移除缓存
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).remove(mobile + Common.VerifyCodeType.REGISTER);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", webUser.getWebUserId());
        jsonObject.put("token", token);
        jsonObject.put("site", site);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "", jsonObject);

    }


    /**
     * 更改手机号
     *
     * @param id
     * @param mobile
     * @param pwd
     * @param verifyCode
     * @return
     */
    @PostMapping("/updateMobile")
    public ReturnStatus updateMobile(Integer id, String mobile, String pwd, String verifyCode) {

        //新手机号验证码是否正确
        String verifyCodeCache = (String) CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(mobile + Common.VerifyCodeType.CHANGE_MOBILE);
        Preconditions.checkNotNull(verifyCodeCache, "验证码已过期，请重新获取！");
        boolean codeValid = java.util.Objects.equals(verifyCode, verifyCodeCache);
        Preconditions.checkArgument(codeValid, "验证码不正确，请重新输入或重新获取！");


        WebUser webUserById = webUserService.getWebUserById(id);
        String encodedPwd = MD5Util.MD5Encode(webUserById.getSalt() + pwd);
        Preconditions.checkArgument(java.util.Objects.equals(encodedPwd, webUserById.getLoginPwd()), "密码输入错误，请重新输入！");

        webUserService.updateMobileById(id, mobile);


        String userKey = webUserById.getId() + ".web";
        String token = JWTokenUtil.createToken(userKey);

        //登录成功删除短信验证码
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).remove(mobile + Common.VerifyCodeType.CHANGE_MOBILE);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "成功！", token);
    }

    /**
     * 更改密码
     *
     * @param id
     * @param pwd
     * @param pwd1
     * @param verifyCode
     * @return
     */
    @PostMapping("/updatePwd")
    public ReturnStatus updatePwd(Integer id, String pwd, String pwd1, String verifyCode) {

        WebUser webUserById = webUserService.getWebUserById(id);
        String mobile = webUserById.getMobile();

        //新手机号验证码是否正确
        String verifyCodeCache = (String) CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(mobile + Common.VerifyCodeType.CHANGE_PASSWORD);
        Preconditions.checkNotNull(verifyCodeCache, "验证码已过期，请重新获取！");
        boolean codeValid = java.util.Objects.equals(verifyCode, verifyCodeCache);
        Preconditions.checkArgument(codeValid, "验证码不正确，请重新输入或重新获取！");

        String encodedPwd = MD5Util.MD5Encode(webUserById.getSalt() + pwd);
        Preconditions.checkArgument(java.util.Objects.equals(encodedPwd, webUserById.getLoginPwd()), "原密码输入错误，请重新输入！");


        webUserService.updatePasswordById(id, pwd1);

        return new ReturnStatus();
    }

    /**
     * 忘记密码
     * @param mobile
     * @param pwd1
     * @param verifyCode
     * @return
     */
    @PostMapping("/forgetPwd")
    public ReturnStatus forgetPwd(String mobile, String pwd1, String verifyCode) {

        WebUser webUserByMobile = webUserService.getWebUserByMobile(mobile);
        Preconditions.checkNotNull(webUserByMobile, "手机号尚未注册，请输入已经注册的手机号！");

        //手机号验证码是否正确
        String verifyCodeCache = (String) CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(mobile + Common.VerifyCodeType.FORGET_PASSWORD);
        Preconditions.checkNotNull(verifyCodeCache, "验证码已过期，请重新获取！");
        boolean codeValid = Objects.equals(verifyCode, verifyCodeCache);
        Preconditions.checkArgument(codeValid, "验证码不正确，请重新输入或重新获取！");

        webUserService.updatePasswordById(webUserByMobile.getId(), pwd1);

        return new ReturnStatus();
    }

    /**
     * 是否注册
     *
     * @param mobile
     * @return
     */
    @PostMapping("/isRegistered")
    public ReturnStatus isRegistered(String mobile) {
        boolean isRegistered = webUserService.isRegistered(mobile);
        Preconditions.checkArgument(!isRegistered, "手机号已注册，请登录！");
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "手机号可以注册！");
    }


    @PostMapping("/sendSms")
    public ReturnStatus sendSms(String mobile, @RequestParam("smsTemplate") SmsUtil.SmsTemplate smsTemplate) throws IOException {


        boolean isRegistered = webUserService.isRegistered(mobile);
        Common.VerifyCodeType verifyCodeType = smsTemplate.getVerifyCodeType();

        switch (verifyCodeType) {
            case REGISTER:
            case CHANGE_MOBILE:
                if (isRegistered) {
                    return new ReturnStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, "手机号已注册！");
                }
                break;
            case CHANGE_PASSWORD:
            case FORGET_PASSWORD:
                if (!isRegistered) {
                    return new ReturnStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, "手机号尚未注册！");
                }
                break;
            default:
                break;
        }

        String result = SmsUtil.send(mobile, smsTemplate);
        JSONObject jsonObject = JSONObject.parseObject(result, JSONObject.class);

        Integer error_code = jsonObject.getInteger("error_code");
        if (error_code == 0) {
            return new ReturnStatus<>();
        } else {
            String message = jsonObject.getString("result");
            throw new ReturnStatusException(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, message);
        }
    }


    @GetMapping("/getUserInfo")
    public ReturnStatus getLoginMsg() {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        HashMap map = new HashMap();
        WebUser webUserById = webUserService.getWebUserById(loginWebUser.getId());
        map.put("id", webUserById.getId());
        map.put("balance", webUserById.getBalance());
        map.put("userName", webUserById.getUserName());
        map.put("loginName", webUserById.getLoginName());
        map.put("headImgUrl", webUserById.getHeadImg());
        return new ReturnStatus(map);
    }

    @GetMapping("/getUserOpenId")
    public ReturnStatus getUserOpenId(String code) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        //WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("unionid", wxMpUser.getUnionId());
        jsonObject.put("openidA", wxMpOAuth2AccessToken.getOpenId());
        //jsonObject.put("nickName", wxMpUser.getNickname());
        //jsonObject.put("headImgUrl", wxMpUser.getHeadImgUrl());

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "", jsonObject);
    }

    private HashMap<String, Object> webUserHasChecked(WebUser webUser) {
        Integer checkStatus = webUser.getCheckStatus();
        if( checkStatus == 2) {
            throw new ReturnStatusException(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, "当前用户尚未审核完成，请耐心等待");
        } else if(checkStatus == 4) {
            throw new ReturnStatusException(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, webUser.getCheckResult());
        }
        return Maps.newHashMap();
    }

}
