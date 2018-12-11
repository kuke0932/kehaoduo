package com.htcs.kehaoduo.system.dao.impl;

import com.htcs.kehaoduo.system.bean.SysUser;
import com.htcs.kehaoduo.system.bean.SysUserModel;
import com.htcs.kehaoduo.system.dao.SysUserDAO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhaokaiyuan
 */
@Repository
public class SysUserDaoImpl implements SysUserDAO {

    @Autowired
    SqlSession sqlSession;

    @Override
    public List<SysUser> findSysUserList(SysUserModel user) {
        return sqlSession.selectList("SysUserMapper.findSysUserList", user);
    }

    @Override
    public SysUser getUserById(Integer userId) {
        // TODO Auto-generated method stub
        return sqlSession.selectOne("SysUserMapper.querySysUserByUserId", userId);
    }

    @Override
    public boolean addUser(SysUser userModel) {
        // TODO Auto-generated method stub
        sqlSession.insert("SysUserMapper.createSysUser", userModel);
        return true;
    }

    @Override
    public int countByLoginName(SysUser userModel) {
        return sqlSession.selectOne("SysUserMapper.countByLoginName", userModel);
    }

    @Override
    public boolean updateUser(SysUser userModel) {
        // TODO Auto-generated method stub
        sqlSession.update("SysUserMapper.updateSysUser", userModel);
        return true;
    }

    @Override
    public boolean deleteUserById(Integer userId) {
        // TODO Auto-generated method stub
        sqlSession.delete("SysUserMapper.delSysUserByUserId", userId);
        return true;
    }

    @Override
    public boolean logicalDeleteUserById(Integer userId) {
        // TODO Auto-generated method stub
        sqlSession.delete("SysUserMapper.logicalDeleteUserById", userId);
        return true;
    }

    @Override
    public List<SysUser> getUserByLogin(SysUser user) {
        return sqlSession.selectList("SysUserMapper.getUserByLogin", user);
    }

    @Override
    public boolean addAgent(SysUser sysUser) {
        sqlSession.insert("SysUserMapper.addAgent", sysUser);
        return true;
    }

    @Override
    public SysUser getUserByLoginName(String username) {
        return sqlSession.selectOne("SysUserMapper.getUserByLoginName", username);
    }

    @Override
    public void updateByLoginName(SysUser sysUser) {
        sqlSession.update("SysUserMapper.updateByLoginName", sysUser);
    }

}
