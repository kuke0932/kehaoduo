package com.htcs.kehaoduo.system.bean;

import java.io.Serializable;

/**
 * 后台系统用户实体
 *
 * @author zhaokaiyuan
 */
public class SysUserModel extends SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer roleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
