package com.htcs.kehaoduo.common.model;


import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * WxCpUser  企业微信用户实体
 */
public class LoginWebUser extends LoginWebUserExt implements Serializable {

    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 用户登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String loginPwd;
    /**
     * 盐
     */
    private String salt;
    /**
     * 用户真名
     */
    private String userName;
    /**
     * 用户状态 1正常 0冻结
     */
    private Integer status;

    /**
     * 审核状态0 未提交审核 1 已保存2 待审核 3 审核通过 4 审核不通过
     */
    private Integer checkStatus;
    /**
     * 最近一次审核结果
     */
    private String checkResult;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 最后登录IP号
     */
    private String lastLoginIp;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 座机
     */
    private String telephone;

    private String apiKey;
    private String apiSecret;

    private String headImgUrl;


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getHeadImg() {
        return headImgUrl;
    }

    public void setHeadImg(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public LoginWebUser copyOf() {
        LoginWebUser user = new LoginWebUser();
        BeanUtils.copyProperties(this, user);
        return user;
    }

}
