package com.htcs.kehaoduo.system.dao;

import com.htcs.kehaoduo.system.bean.SysUser;
import com.htcs.kehaoduo.system.bean.SysUserModel;

import java.util.List;

/**
 * @author zhaokaiyuan
 */
public interface SysUserDAO {
    /**
     * 获取用户列表
     *
     * @param user
     * @return
     */
    public List<SysUser> findSysUserList(SysUserModel user);

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    public SysUser getUserById(Integer userId);

    /**
     * 添加用户
     *
     * @param userModel
     * @return
     */
    public boolean addUser(SysUser userModel);

    /**
     * 根据登录名查询SysUser数量
     *
     * @param userModel
     * @return
     */
    int countByLoginName(SysUser userModel);

    /**
     * 更新用户
     *
     * @param userModel
     * @return
     */
    public boolean updateUser(SysUser userModel);

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    public boolean deleteUserById(Integer userId);

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    public boolean logicalDeleteUserById(Integer userId);

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    public List<SysUser> getUserByLogin(SysUser user);

    /**
     * 新增代理商
     *
     * @param sysUser
     * @return
     */
    boolean addAgent(SysUser sysUser);

    /**
     * 根据username查询用户
     *
     * @param username 用户名
     * @return
     */
    SysUser getUserByLoginName(String username);

    /**
     * 根据username修改密码
     *
     * @param sysUser
     */
    void updateByLoginName(SysUser sysUser);
}
