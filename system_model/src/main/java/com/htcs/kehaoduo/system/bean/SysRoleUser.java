package com.htcs.kehaoduo.system.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联表
 *
 * @author taotao
 */
public class SysRoleUser implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer authUserId;
    private Integer roleId;
    private Integer userId;
    private Date createTime;

    private Integer logicalDel = 0;

    public Integer getLogicalDel() {
        return logicalDel;
    }

    public void setLogicalDel(Integer logicalDel) {
        this.logicalDel = logicalDel;
    }

    public Integer getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(Integer authUserId) {
        this.authUserId = authUserId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
