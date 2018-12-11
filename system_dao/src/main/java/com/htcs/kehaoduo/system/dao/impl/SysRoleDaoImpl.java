package com.htcs.kehaoduo.system.dao.impl;

import com.htcs.kehaoduo.common.Common;
import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.dao.SysRoleDAO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhaokaiyuan
 */
@Repository
public class SysRoleDaoImpl implements SysRoleDAO {


    @Autowired
    SqlSession sqlSession;

    /*
     * 获取角色列表
     */
    @Override
    public List<SysRole> findSysRoleList(SysRole role) {
        return sqlSession.selectList("SysRoleMapper.findAllSysRole", role);
    }

    /**
     * 根据角色ID，获取角色
     *
     * @param roleId
     * @return
     */
    @Override
    public SysRole getRoleById(Integer roleId) {
        // TODO Auto-generated method stub
        return sqlSession.selectOne("SysRoleMapper.querySysRoleByRoleId", roleId);
    }

    /**
     * 添加角色
     *
     * @param roleModel
     * @return
     */
    @Override
    public boolean addRole(SysRole roleModel) {
        // TODO Auto-generated method stub
        sqlSession.insert("SysRoleMapper.createSysRole", roleModel);
        return true;
    }

    /**
     * 更新角色
     *
     * @param roleModel
     * @return
     */
    @Override
    public boolean updateRole(SysRole roleModel) {
        // TODO Auto-generated method stub
        sqlSession.update("SysRoleMapper.updateSysRole", roleModel);
        return true;
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @Override
    public boolean deleteRoleById(Integer roleId) {
        // TODO Auto-generated method stub
        sqlSession.delete("SysRoleMapper.delSysRoleByRoleId", roleId);
        return true;
    }

    /**
     * 获取子角色列表
     *
     * @param roleId
     * @return
     */
    @Override
    public List<SysRole> findRoleByParentId(Integer roleId) {
        // TODO Auto-generated method stub
        return sqlSession.selectList("SysRoleMapper.findRoleByParentId", roleId);
    }

    /**
     * 逻辑删除
     *
     * @param roleId
     * @return
     */
    @Override
    public boolean logicalDeleteRoleById(Integer roleId) {
        // TODO Auto-generated method stub
        SysRole role = new SysRole();
        role.setId(roleId);
        role.setLogicalDel(Common.LOGICALDEL_YES);
        sqlSession.update("SysRoleMapper.logicalDeleteRoleById", role);
        return true;
    }

}
