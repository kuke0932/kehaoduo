package com.htcs.kehaoduo.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhaokaiyuan
 * @create 2017-10-20 9:35
 **/
public class LoginSysUserExt extends BaseModel implements Serializable {

    Integer id;
    Integer sysUserId;
    String openid;
    String unionid;
    String area;
    BigDecimal canWithdrawBalance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public BigDecimal getCanWithdrawBalance() {
        return canWithdrawBalance;
    }

    public void setCanWithdrawBalance(BigDecimal canWithdrawBalance) {
        this.canWithdrawBalance = canWithdrawBalance;
    }
}
