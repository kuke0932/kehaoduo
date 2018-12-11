package com.htcs.kehaoduo.system.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询广告主列表dto
 *
 * @author Dragon
 * @create 2017-12-01 15:17
 **/
public class AdvertiserDto implements Serializable {

    private static final long serialVersionUID = -6265449841580949639L;
    /**
     * 代理区域
     */
    private String area;
    /**
     * 代理商id
     */
    private Integer sysUserId;
    /**
     * 广告主名字
     */
    private String advertiserName;
    /**
     * 店铺名或手机号
     */
    private String shopNameOrTel;
    /**
     * 主营业务
     */
    private String businessLine;
    /**
     * 审核状态
     */
    private Integer checkStatus;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getAdvertiserName() {
        return advertiserName;
    }

    public void setAdvertiserName(String advertiserName) {
        this.advertiserName = advertiserName;
    }

    public String getShopNameOrTel() {
        return shopNameOrTel;
    }

    public void setShopNameOrTel(String shopNameOrTel) {
        this.shopNameOrTel = shopNameOrTel;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
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
}
