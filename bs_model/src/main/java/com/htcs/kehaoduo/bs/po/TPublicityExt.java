package com.htcs.kehaoduo.bs.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 推广信息扩展实体
 *
 * @author Dragon
 * @create 2017-11-22 8:54
 **/
public class TPublicityExt implements Serializable {

    private static final long serialVersionUID = 6972514864463462231L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 推广id
     */
    private Integer publicityId;
    /**
     * 正文文本
     */
    private String contentText;
    /**
     * 正文图片
     */
    private String contentImage;
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

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
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
        return "tPublicityExt{" +
                "id=" + id +
                ", publicityId=" + publicityId +
                ", contentText='" + contentText + '\'' +
                ", contentImage='" + contentImage + '\'' +
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
