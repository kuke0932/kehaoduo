package com.htcs.kehaoduo.system.dao;

import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.bean.SysRoleUser;
import com.htcs.kehaoduo.system.bean.SysRoleUserModel;
import com.htcs.kehaoduo.system.bean.SysUser;

import java.util.List;

/**
 * @author zhaokaiyuan
 */
public interface SysRoleUserDAO {
    /**
     * 根据角色ID获取用户列表
     *
     * @param sysRoleUserModel
     * @return
     */
    public List<SysUser> findSysUserListByRoleUser(SysRoleUserModel sysRoleUserModel);

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
     * 根据用户获取角色列表
     *
     * @param userId
     * @return
     */
    public List<SysRole> findRoleListByUserId(int userId);

    /**
     * 根据角色ID获取用户列表
     *
     * @param model
     * @return
     */
    List<SysUser> listByRoleId(SysRoleUserModel model);
}
