package com.htcs.kehaoduo.system.service.impl;

import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.bean.SysRoleUser;
import com.htcs.kehaoduo.system.bean.SysRoleUserModel;
import com.htcs.kehaoduo.system.bean.SysUser;
import com.htcs.kehaoduo.system.dao.SysRoleUserDAO;
import com.htcs.kehaoduo.system.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * @author zhaokaiyuan
 */
public class SysRoleUserServiceImpl implements SysRoleUserService {
    @Autowired(required = false)
    private SysRoleUserDAO roleUserDao;

    /**
     * 根据角色ID获取用户列表
     *
     * @param model
     * @return
     */
    @Override
    public List<SysUser> findSysUserListByRoleUser(SysRoleUserModel model) {
        return roleUserDao.findSysUserListByRoleUser(model);
    }

    /**
     * 根据角色ID获取用户列表
     *
     * @param model
     * @return
     */
    @Override
    public List<SysUser> listByRoleId(SysRoleUserModel model) {
        return roleUserDao.listByRoleId(model);
    }

    /**
     * 根据用户ID获取角色列表
     *
     * @param user
     * @param page
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> findSyRoleListByUserId(Integer userId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 添加用户角色关系
     *
     * @param userModel
     * @return
     */
    @Override
    public boolean addRoleUser(SysRoleUser roleUser) {
        return roleUserDao.addRoleUser(roleUser);
    }

    /**
     * 删除用户角色关系
     *
     * @param userId
     * @return
     */
    @Override
    public boolean deleteRoleUser(SysRoleUser roleUser) {
        return roleUserDao.deleteRoleUser(roleUser);
    }

    @Override
    public boolean addRoleUsers(String userIds, Integer roleId) {
        String[] userIdArray = userIds.split(",");
        SysRoleUser roleUser = new SysRoleUser();
        for (String userId : userIdArray) {
            roleUser.setUserId(Integer.valueOf(userId));
            roleUser.setRoleId(roleId);
            addRoleUser(roleUser);
        }
        return true;
    }

    @Override
    public boolean deleteRoleUsers(String userIds, Integer roleId) {
        String[] userIdArray = userIds.split(",");
        SysRoleUser roleUser = new SysRoleUser();
        for (String userId : userIdArray) {
            roleUser.setUserId(Integer.valueOf(userId));
            roleUser.setRoleId(roleId);
            deleteRoleUser(roleUser);
        }
        return true;

    }

    /**
     * 根据用户获取角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> findRoleListByUserId(int userId) {
        // TODO Auto-generated method stub
        return roleUserDao.findRoleListByUserId(userId);
    }

}
