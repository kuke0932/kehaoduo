package com.htcs.kehaoduo.bs.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 推广效果实体
 *
 * @author Dragon
 * @create 2017-10-20 9:47
 **/
public class TPublicityResult implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 推广主键
     */
    private Integer publicityId;
    /**
     * 全部推广次数，冗余字段
     */
    private Integer totalReadCount;
    /**
     * 扫码者微信id, 可能获取不到，转发者openId
     */
    private String userWechatId;
    /**
     * 扫码（转发）者昵称
     */
    private String userNickname;
    /**
     * 扫码（转发）者头像
     */
    private String userHeadImage;
    /**
     * 扫码（转发）时间
     */
    private Date operateTime;
    /**
     * 发送人数
     */
    private Integer toUserCount;
    /**
     * 阅读次数
     */
    private Integer readCount;
    /**
     * 推广类型 1扫码2转发
     */
    private Byte publicityType;
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

    public Integer getPublicityId() {
        return publicityId;
    }

    public void setPublicityId(Integer publicityId) {
        this.publicityId = publicityId;
    }

    public Integer getTotalReadCount() {
        return totalReadCount;
    }

    public void setTotalReadCount(Integer totalReadCount) {
        this.totalReadCount = totalReadCount;
    }

    public String getUserWechatId() {
        return userWechatId;
    }

    public void setUserWechatId(String userWechatId) {
        this.userWechatId = userWechatId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage = userHeadImage;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getToUserCount() {
        return toUserCount;
    }

    public void setToUserCount(Integer toUserCount) {
        this.toUserCount = toUserCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Byte getPublicityType() {
        return publicityType;
    }

    public void setPublicityType(Byte publicityType) {
        this.publicityType = publicityType;
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
        return "TPublicityResult{" +
                "id=" + id +
                ", publicityId=" + publicityId +
                ", totalReadCount=" + totalReadCount +
                ", userWechatId='" + userWechatId + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userHeadImage='" + userHeadImage + '\'' +
                ", operateTime=" + operateTime +
                ", toUserCount=" + toUserCount +
                ", readCount=" + readCount +
                ", publicityType=" + publicityType +
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