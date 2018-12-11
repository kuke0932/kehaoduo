package com.htcs.kehaoduo.system.service;

import com.htcs.kehaoduo.system.bean.SysFunction;
import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.bean.SysRoleFunction;

import java.util.List;

/**
 * @author zhaokaiyuan
 */
public interface SysRoleFunctionService {
    /**
     * 根据菜单ID获取角色列表
     *
     * @param roleId
     * @return
     */
    public List<SysRoleFunction> findRoleFunctionListByRoleId(Integer roleId);

    /**
     * 添加菜单角色关系
     *
     * @param sysRoleFunction
     * @return
     */
    public boolean addRoleFunction(SysRoleFunction sysRoleFunction);

    /**
     * 删除菜单角色关系
     *
     * @param roleId
     * @return
     */
    public boolean deleteRoleFunctionByRoleId(Integer roleId);

    /**
     * 添加菜单角色关系
     *
     * @param funtionIds
     * @param roleId
     * @return
     */
    public boolean addRoleFunctions(String funtionIds, Integer roleId);

    /**
     * 授权
     *
     * @param functionIds
     * @param roleId
     * @return
     */
    public boolean setRoleFunctions(String functionIds, Integer roleId);

    /**
     * 根据角色列表获取功能列表
     *
     * @param roleList
     * @return
     */
    public List<SysFunction> findFunctionListByRoleList(List<SysRole> roleList);

}
