package com.htcs.kehaoduo.system.service;

import com.htcs.kehaoduo.system.bean.SysUser;
import com.htcs.kehaoduo.system.bean.SysUserModel;

import java.util.List;

/**
 * @author zhaokaiyuan
 */
public interface SysUserService {

    /**
     * 查询用户列表
     *
     * @param user
     * @return
     */
    public List<SysUser> findSysUserList(SysUserModel user);

    /**
     * 查询用户
     *
     * @param userId
     * @return
     */
    public SysUser getUserById(Integer userId);

    /**
     * 查询用户
     *
     * @param loginName
     * @return
     */
    public SysUser getUserByLoginName(String loginName);
    /**
     * 添加用户
     *
     * @param userModel
     * @return
     */
    public boolean addUser(SysUser userModel);

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
     * 修改密码
     *
     * @param username 用户名
     * @param oldPwd   旧密码
     * @param newPwd   新密码
     * @return
     */
    boolean updatePassword(String username, String oldPwd, String newPwd);
}
