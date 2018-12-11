package com.htcs.kehaoduo.system.service;

import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.bean.SysRoleUser;
import com.htcs.kehaoduo.system.bean.SysRoleUserModel;
import com.htcs.kehaoduo.system.bean.SysUser;

import java.util.List;

/**
 * @author zhaokaiyuan
 */
public interface SysRoleUserService {
    /**
     * 根据角色ID获取用户列表
     *
     * @param model
     * @return
     */
    public List<SysUser> findSysUserListByRoleUser(SysRoleUserModel model);

    /**
     * 根据角色ID获取用户列表
     *
     * @param model
     * @return
     */
    List<SysUser> listByRoleId(SysRoleUserModel model);

    /**
     * 根据用户ID获取角色列表
     *
     * @param userId
     * @return
     */
    public List<SysRole> findSyRoleListByUserId(Integer userId);

    /**
     * 添加用户角色关系
     *
     * @param roleUser
     * @return
     */
    public boolean addRoleUser(SysRoleUser roleUser);

    /**
     * 删除用户角色关系
     *
     * @param roleUser
     * @return
     */
    public boolean deleteRoleUser(SysRoleUser roleUser);

    /**
     * 添加角色用户关系
     *
     * @param userIds
     * @param roleId
     * @return boolean
     */
    public boolean addRoleUsers(String userIds, Integer roleId);

    /**
     * 删除角色用户关系
     *
     * @param userIds
     * @param roleId
     * @return
     */
    public boolean deleteRoleUsers(String userIds, Integer roleId);

    /**
     * 根据用户获取角色列表
     *
     * @param userId
     * @return
     */
    public List<SysRole> findRoleListByUserId(int userId);
}
