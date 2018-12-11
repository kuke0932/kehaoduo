package com.htcs.kehaoduo.system.dao;

import com.htcs.kehaoduo.system.bean.SysRole;

import java.util.List;

/**
 * @author zhaokaiyuan
 */
public interface SysRoleDAO {

    /**
     * 获取角色列表
     *
     * @param role
     * @return
     */
    public List<SysRole> findSysRoleList(SysRole role);

    /**
     * 根据角色ID，获取角色
     *
     * @param roleId
     * @return
     */
    public SysRole getRoleById(Integer roleId);

    /**
     * 添加角色
     *
     * @param roleModel
     * @return
     */
    public boolean addRole(SysRole roleModel);

    /**
     * 更新角色
     *
     * @param roleModel
     * @return
     */
    public boolean updateRole(SysRole roleModel);

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    public boolean deleteRoleById(Integer roleId);

    /**
     * 获取子角色列表
     *
     * @param roleId
     * @return
     */
    public List<SysRole> findRoleByParentId(Integer roleId);

    /**
     * 逻辑删除
     *
     * @param roleId
     * @return
     */
    public boolean logicalDeleteRoleById(Integer roleId);
}
