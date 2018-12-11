package com.htcs.kehaoduo.system.dao.impl;

import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.bean.SysRoleUser;
import com.htcs.kehaoduo.system.bean.SysRoleUserModel;
import com.htcs.kehaoduo.system.bean.SysUser;
import com.htcs.kehaoduo.system.dao.SysRoleUserDAO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * @author zhaokaiyuan
 */
public class SysRoleUserDaoImpl implements SysRoleUserDAO {

    @Autowired
    SqlSession sqlSession;

    /**
     * 根据角色ID获取用户列表
     *
     * @param sysRoleUserModel
     * @return
     */
    @Override
    public List<SysUser> findSysUserListByRoleUser(SysRoleUserModel sysRoleUserModel) {
        return sqlSession.selectList("SysRoleUserMapper.findSysUserListByRole", sysRoleUserModel);
    }

    /**
     * 根据用户ID获取角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> findSyRoleListByUserId(Integer userId) {
        return sqlSession.selectList("SysRoleUserMapper.findSyRoleListByUserId", userId);

    }

    /**
     * 添加用户角色关系
     *
     * @param roleUser
     * @return
     */
    @Override
    public boolean addRoleUser(SysRoleUser roleUser) {
        sqlSession.insert("SysRoleUserMapper.addRoleUser", roleUser);
        return true;
    }

    /**
     * 删除用户角色关系
     *
     * @param userId
     * @return
     */
    @Override
    public boolean deleteRoleUser(SysRoleUser roleUser) {
        sqlSession.delete("SysRoleUserMapper.deleteRoleUser", roleUser);
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
        return sqlSession.selectList("SysRoleMapper.findRoleListByUserId", userId);
    }

    /**
     * 根据角色ID获取用户列表
     *
     * @param model
     * @return
     */
    @Override
    public List<SysUser> listByRoleId(SysRoleUserModel model) {
        return sqlSession.selectList("SysRoleUserMapper.listByRoleId", model);
    }

}
