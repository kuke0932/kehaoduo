package com.htcs.kehaoduo.bs.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取广告主消费明细所需参数实体
 *
 * @author Dragon
 * @create 2017-12-04 11:03
 **/
public class AdvertiserAndPublicityDto implements Serializable {

    private static final long serialVersionUID = -7104400328284042909L;

    /**
     * 用户id
     */
    private Integer sysUserId;
    /**
     * 推广类型(1扫码，2转发)
     */
    private Byte publicityType;
    /**
     * 正文类型(1文本2图文)
     */
    private Byte contentType;
    /**
     * 广告主名字
     */
    private String userName;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 代理区域
     */
    private String area;
    /**
     * 广告标题
     */
    private String publicityTitle;
    /**
     * 审核状态
     */
    private Integer checkStatus;

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Byte getPublicityType() {
        return publicityType;
    }

    public void setPublicityType(Byte publicityType) {
        this.publicityType = publicityType;
    }

    public Byte getContentType() {
        return contentType;
    }

    public void setContentType(Byte contentType) {
        this.contentType = contentType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPublicityTitle() {
        return publicityTitle;
    }

    public void setPublicityTitle(String publicityTitle) {
        this.publicityTitle = publicityTitle;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }
}
