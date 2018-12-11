package com.htcs.kehaoduo.system.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联表
 *
 * @author taotao
 */
public class SysRoleFunction implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer authFunctionId;
    private Integer roleId;
    private Integer functionId;
    private Date createTime;

    public Integer getAuthFunctionId() {
        return authFunctionId;
    }

    public void setAuthFunctionId(Integer authFunctionId) {
        this.authFunctionId = authFunctionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
