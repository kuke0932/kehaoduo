package com.htcs.kehaoduo.bs.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户电话
 *
 * @author shenyahui
 * @create 2017-11-06 11:47
 **/
public class TPublicityPhone implements Serializable {

    private static final long serialVersionUID = -5821887212538182321L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer webUserId;
    /**
     * 推广主键
     */
    private Integer publicityId;
    /**
     * 电话
     */
    private String phone;
    /**
     * 区域
     */
    private String validAreaCode;

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

    public Integer getPublicityId() {
        return publicityId;
    }

    public void setPublicityId(Integer publicityId) {
        this.publicityId = publicityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getValidAreaCode() {
        return validAreaCode;
    }

    public void setValidAreaCode(String validAreaCode) {
        this.validAreaCode = validAreaCode;
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
}
