package com.htcs.kehaoduo.common.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginSysUserMsg implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 登录用户
     */
    private LoginSysUser user;

    /**
     * 角色列表
     */

    private List<LoginSysRole> roleList;
    /**
     * 功能列表
     */

    private List<LoginSysFunction> functionList;
    private Set<String> roleSet = new HashSet<String>();
    private Set<String> functionUrlSet = new HashSet<String>();
    /**
     * 功能树
     */
    private LoginSysFunction functionTree;
    /**
     * 角色树
     */
    private LoginSysFunction roleTree;

    public LoginSysUser getUser() {
        return user;
    }

    public void setUser(LoginSysUser user) {
        this.user = user;
    }

    public List<LoginSysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<LoginSysRole> roleList) {
        if (roleList != null) {
            for (LoginSysRole role : roleList) {
                roleSet.add(role.getRoleName());
            }
        }
        this.roleList = roleList;
    }

    public LoginSysFunction getFunctionTree() {
        return functionTree;
    }

    public void setFunctionTree(LoginSysFunction functionTree) {
        this.functionTree = functionTree;
    }

    public LoginSysFunction getRoleTree() {
        return roleTree;
    }

    public void setRoleTree(LoginSysFunction roleTree) {
        this.roleTree = roleTree;
    }

    public List<LoginSysFunction> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<LoginSysFunction> functionList) {
        if (functionList != null) {
            for (LoginSysFunction function : functionList) {
                functionUrlSet.add(function.getFunctionUrl());
            }
        }
        this.functionList = functionList;
    }


}
