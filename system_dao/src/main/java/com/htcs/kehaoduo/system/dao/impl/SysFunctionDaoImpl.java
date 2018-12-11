package com.htcs.kehaoduo.system.dao.impl;

import com.htcs.kehaoduo.common.Common;
import com.htcs.kehaoduo.system.bean.SysFunction;
import com.htcs.kehaoduo.system.dao.SysFunctionDAO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * @author zhaokaiyuan
 */
public class SysFunctionDaoImpl implements SysFunctionDAO {

    @Autowired
    SqlSession sqlSession;

    /*
     * 获取菜单列表
     * @param sysFunction
     * @return
     */
    @Override
    public List<SysFunction> findSysFunctionList(SysFunction sysFunction) {
        return sqlSession.selectList("SysFunctionMapper.findAllSysFunction", sysFunction);
    }

    /**
     * 根据菜单ID，获取菜单
     *
     * @param functionId
     * @return
     */
    @Override
    public SysFunction getFunctionById(Integer functionId) {
        // TODO Auto-generated method stub
        return sqlSession.selectOne("SysFunctionMapper.querySysFunctionByFunctionId", functionId);
    }

    /**
     * 添加菜单
     *
     * @param sysFunction
     * @return
     */
    @Override
    public boolean addFunction(SysFunction sysFunction) {
        // TODO Auto-generated method stub
        sqlSession.insert("SysFunctionMapper.createSysFunction", sysFunction);
        return true;
    }

    /**
     * 更新菜单
     *
     * @param sysFunction
     * @return
     */
    @Override
    public boolean updateFunction(SysFunction sysFunction) {
        // TODO Auto-generated method stub
        sqlSession.update("SysFunctionMapper.updateSysFunction", sysFunction);
        return true;
    }

    /**
     * 删除菜单
     *
     * @param functionId
     * @return
     */
    @Override
    public boolean deleteFunctionById(Integer functionId) {
        // TODO Auto-generated method stub
        sqlSession.delete("SysFunctionMapper.delSysFunctionByFunctionId", functionId);
        return true;
    }

    /**
     * 获取子菜单列表
     */
    @Override
    public List<SysFunction> findFunctionByParentId(Integer parentId) {
        // TODO Auto-generated method stub
        return sqlSession.selectList("SysFunctionMapper.findFunctionByParentId", parentId);
    }

    /**
     * 逻辑删除
     */
    @Override
    public boolean logicalDeleteFunctionById(Integer functionId) {
        // TODO Auto-generated method stub
        SysFunction function = new SysFunction();
        function.setFunctionId(functionId);
        function.setLogicalDel(Common.LOGICALDEL_YES);
        sqlSession.update("SysFunctionMapper.logicalDeleteFunctionById", function);
        return true;
    }

}
