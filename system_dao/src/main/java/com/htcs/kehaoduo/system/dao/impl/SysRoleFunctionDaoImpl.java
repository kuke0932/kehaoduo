package com.htcs.kehaoduo.system.dao.impl;

import com.htcs.kehaoduo.system.bean.SysFunction;
import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.bean.SysRoleFunction;
import com.htcs.kehaoduo.system.dao.SysRoleFunctionDAO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * @author zhaokaiyuan
 */
public class SysRoleFunctionDaoImpl implements SysRoleFunctionDAO {

    @Autowired
    SqlSession sqlSession;

    /**
     * 根据菜单ID获取角色列表
     *
     * @param sysRoleFunction
     * @return
     */
    @Override
    public List<SysRoleFunction> findRoleFunctionListByRoleId(SysRoleFunction sysRoleFunction) {
        return sqlSession.selectList("SysRoleFunctionMapper.findRoleFunctionListByRoleId", sysRoleFunction);
    }

    /**
     * 添加菜单角色关系
     *
     * @param sysRoleFunction
     * @return
     */
    @Override
    public boolean addRoleFunction(SysRoleFunction sysRoleFunction) {
        sqlSession.insert("SysRoleFunctionMapper.addRoleFunction", sysRoleFunction);
        return true;
    }

    /**
     * 删除菜单角色关系
     *
     * @param sysRoleFunction
     * @return
     */
    @Override
    public boolean deleteRoleFunctionByRoleId(SysRoleFunction sysRoleFunction) {
        sqlSession.delete("SysRoleFunctionMapper.deleteRoleFunctionByRoleId", sysRoleFunction);
        return true;
    }

    /**
     * 根据橘色列表获取功能列表
     *
     * @param roleList
     * @return
     */
    @Override
    public List<SysFunction> findFunctionListByRoleList(List<SysRole> roleList) {
        return sqlSession.selectList("SysFunctionMapper.findFunctionListByRoleList", roleList);
    }

}
