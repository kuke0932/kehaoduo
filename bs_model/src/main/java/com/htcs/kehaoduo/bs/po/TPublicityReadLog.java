package com.htcs.kehaoduo.bs.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 点击日志实体
 *
 * @author Dragon
 * @create 2017-10-20 9:47
 **/
public class TPublicityReadLog implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * zhujain
     */
    private Integer id;
    /**
     * 推广ID
     */
    private Integer publicityId;
    /**
     * 推广者userid
     */
    private Integer fromWebUserId;
    /**
     * 推广者openid
     */
    private String fromOpenId;
    /**
     * 读者openid
     */
    private String readerOpenId;
    /**
     * 该次阅读文章是否有效0 无效1有效
     */
    private Byte isValid;
    /**
     * 阅读时间
     */
    private Date readTime;
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

    public String getFromOpenId() {
        return fromOpenId;
    }

    public void setFromOpenId(String fromOpenId) {
        this.fromOpenId = fromOpenId;
    }

    public String getReaderOpenId() {
        return readerOpenId;
    }

    public void setReaderOpenId(String readerOpenId) {
        this.readerOpenId = readerOpenId;
    }

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
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

    public Integer getFromWebUserId() {
        return fromWebUserId;
    }

    public void setFromWebUserId(Integer fromWebUserId) {
        this.fromWebUserId = fromWebUserId;
    }

    @Override
    public String toString() {
        return "TPublicityReadLog{" +
                "id=" + id +
                ", publicityId=" + publicityId +
                ", fromWebUserId=" + fromWebUserId +
                ", fromOpenId='" + fromOpenId + '\'' +
                ", readerOpenId='" + readerOpenId + '\'' +
                ", isValid=" + isValid +
                ", readTime=" + readTime +
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