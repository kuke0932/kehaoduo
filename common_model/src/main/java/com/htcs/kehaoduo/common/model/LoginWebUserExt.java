package com.htcs.kehaoduo.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhaokaiyuan
 * @create 2017-10-20 9:35
 **/
public class LoginWebUserExt extends BaseModel implements Serializable {

    Integer id;
    Integer webUserId;
    String openidA;
    String openidB;
    String unionid;
    String lastLoginType;
    String belongAppId;
    BigDecimal balance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenidA() {
        return openidA;
    }

    public void setOpenidA(String openidA) {
        this.openidA = openidA;
    }

    public String getOpenidB() {
        return openidB;
    }

    public void setOpenidB(String openidB) {
        this.openidB = openidB;
    }

    public String getLastLoginType() {
        return lastLoginType;
    }

    public void setLastLoginType(String lastLoginType) {
        this.lastLoginType = lastLoginType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getWebUserId() {
        return webUserId;
    }

    public void setWebUserId(Integer webUserId) {
        this.webUserId = webUserId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getBelongAppId() {
        return belongAppId;
    }

    public void setBelongAppId(String belongAppId) {
        this.belongAppId = belongAppId;
    }
}
