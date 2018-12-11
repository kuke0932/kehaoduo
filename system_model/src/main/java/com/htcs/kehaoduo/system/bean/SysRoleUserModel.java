package com.htcs.kehaoduo.system.bean;

/**
 * @author zhaokaiyuan
 */
public class SysRoleUserModel extends SysRoleUser {

    private static final long serialVersionUID = 1L;
    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
