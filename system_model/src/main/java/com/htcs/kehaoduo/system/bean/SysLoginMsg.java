package com.htcs.kehaoduo.system.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhaokaiyuan
 */
public class SysLoginMsg implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 登录用户
     */
    private SysUser user;
    /**
     * 角色列表
     */
    private List<SysRole> roleList;
    /**
     * 功能列表
     */
    private List<SysFunction> functionList;
    private Set<String> roleSet = new HashSet<String>();
    private Set<String> functionUrlSet = new HashSet<String>();
    /**
     * 功能树
     */
    private SysFunction functionTree;
    /**
     * 角色树
     */
    private SysFunction roleTree;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        if (roleList != null) {
            for (SysRole role : roleList) {
                roleSet.add(role.getRoleName());
            }
        }
        this.roleList = roleList;
    }

    public SysFunction getFunctionTree() {
        return functionTree;
    }

    public void setFunctionTree(SysFunction functionTree) {
        this.functionTree = functionTree;
    }

    public SysFunction getRoleTree() {
        return roleTree;
    }

    public void setRoleTree(SysFunction roleTree) {
        this.roleTree = roleTree;
    }

    public List<SysFunction> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SysFunction> functionList) {
        if (functionList != null) {
            for (SysFunction function : functionList) {
                functionUrlSet.add(function.getFunctionUrl());
            }
        }
        this.functionList = functionList;
    }


}
