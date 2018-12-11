package com.htcs.kehaoduo.system.dao;

import com.htcs.kehaoduo.system.bean.SysFunction;
import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.bean.SysRoleFunction;

import java.util.List;

/**
 * @author zhaokaiyuan
 */
public interface SysRoleFunctionDAO {
    /**
     * 根据菜单ID获取角色列表
     *
     * @param sysRoleFunction
     * @return
     */
    public List<SysRoleFunction> findRoleFunctionListByRoleId(SysRoleFunction sysRoleFunction);

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
     * @param sysRoleFunction
     * @return
     */
    public boolean deleteRoleFunctionByRoleId(SysRoleFunction sysRoleFunction);

    /**
     * 根据橘色列表获取功能列表
     *
     * @param roleList
     * @return
     */
    public List<SysFunction> findFunctionListByRoleList(List<SysRole> roleList);

}
