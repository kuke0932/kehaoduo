package com.htcs.kehaoduo.bs.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class TPublicityCheckLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 推广id
     */
    private Integer publicityId;
    /**
     * 推广所属用户id
     */
    private Integer publicityWebUserId;
    /**
     * 审核状态0 未提交审核 1 已保存2 待审核 3 审核通过 4 审核不通过
     */
    private Byte fromStatus;
    /**
     * 审核状态0 未提交审核 1 已保存2 待审核 3 审核通过 4 审核不通过
     */
    private Byte toStatus;
    /**
     * 审核结果
     */
    private String checkResult;
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

    public Integer getPublicityWebUserId() {
        return publicityWebUserId;
    }

    public void setPublicityWebUserId(Integer publicityWebUserId) {
        this.publicityWebUserId = publicityWebUserId;
    }

    public Byte getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(Byte fromStatus) {
        this.fromStatus = fromStatus;
    }

    public Byte getToStatus() {
        return toStatus;
    }

    public void setToStatus(Byte toStatus) {
        this.toStatus = toStatus;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
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
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TPublicityCheckLog other = (TPublicityCheckLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPublicityId() == null ? other.getPublicityId() == null : this.getPublicityId().equals(other.getPublicityId()))
            && (this.getPublicityWebUserId() == null ? other.getPublicityWebUserId() == null : this.getPublicityWebUserId().equals(other.getPublicityWebUserId()))
            && (this.getFromStatus() == null ? other.getFromStatus() == null : this.getFromStatus().equals(other.getFromStatus()))
            && (this.getToStatus() == null ? other.getToStatus() == null : this.getToStatus().equals(other.getToStatus()))
            && (this.getCheckResult() == null ? other.getCheckResult() == null : this.getCheckResult().equals(other.getCheckResult()))
            && (this.getLogicalDel() == null ? other.getLogicalDel() == null : this.getLogicalDel().equals(other.getLogicalDel()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateName() == null ? other.getCreateName() == null : this.getCreateName().equals(other.getCreateName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateName() == null ? other.getUpdateName() == null : this.getUpdateName().equals(other.getUpdateName()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPublicityId() == null) ? 0 : getPublicityId().hashCode());
        result = prime * result + ((getPublicityWebUserId() == null) ? 0 : getPublicityWebUserId().hashCode());
        result = prime * result + ((getFromStatus() == null) ? 0 : getFromStatus().hashCode());
        result = prime * result + ((getToStatus() == null) ? 0 : getToStatus().hashCode());
        result = prime * result + ((getCheckResult() == null) ? 0 : getCheckResult().hashCode());
        result = prime * result + ((getLogicalDel() == null) ? 0 : getLogicalDel().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateName() == null) ? 0 : getCreateName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateName() == null) ? 0 : getUpdateName().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", publicityId=").append(publicityId);
        sb.append(", publicityWebUserId=").append(publicityWebUserId);
        sb.append(", fromStatus=").append(fromStatus);
        sb.append(", toStatus=").append(toStatus);
        sb.append(", checkResult=").append(checkResult);
        sb.append(", logicalDel=").append(logicalDel);
        sb.append(", createBy=").append(createBy);
        sb.append(", createName=").append(createName);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateName=").append(updateName);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}