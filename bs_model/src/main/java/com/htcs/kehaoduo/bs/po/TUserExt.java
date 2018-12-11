package com.htcs.kehaoduo.bs.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户扩展实体
 *
 * @author Dragon
 * @create 2017-10-20 9:47
 **/
public class TUserExt implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户表外键
     */
    private Integer id;
    /**
     * 前台注册用户id
     */
    private Integer webUserId;
    /**
     * 主公众号用户openid
     */
    private String openidA;
    /**
     * 充值公众号的用户openid
     */
    private String openidB;
    /**
     * 上次登录类型 0 未登录 1 广告主 2 普通用户
     */
    private Byte lastLoginType;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 逻辑删除标志   1 删除 0 未删除
     */
    private Byte logicalDel;
    /**
     * 创建者ID
     */
    private Integer createBy;
    /**
     * 创建者名字
     */
    private String createName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者ID
     */
    private Integer updateBy;
    /**
     * 更新者名字
     */
    private String updateName;
    /**
     * 更新时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWebUserId() {
        return webUserId;
    }

    public void setWebUserId(Integer webUserId) {
        this.webUserId = webUserId;
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

    public Byte getLastLoginType() {
        return lastLoginType;
    }

    public void setLastLoginType(Byte lastLoginType) {
        this.lastLoginType = lastLoginType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Byte getLogicalDel() {
        return logicalDel;
    }

    public void setLogicalDel(Byte logicalDel) {
        this.logicalDel = logicalDel;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TUserExt{" +
                "id=" + id +
                ", webUserId=" + webUserId +
                ", openidA='" + openidA + '\'' +
                ", openidB='" + openidB + '\'' +
                ", lastLoginType=" + lastLoginType +
                ", balance=" + balance +
                ", logicalDel=" + logicalDel +
                ", createBy=" + createBy +
                ", createName='" + createName + '\'' +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateName='" + updateName + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}