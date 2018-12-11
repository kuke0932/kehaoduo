package com.htcs.kehaoduo.system.dao;

import com.htcs.kehaoduo.system.bean.SysFunction;

import java.util.List;

/**
 * @author zhaokaiyuan
 */
public interface SysFunctionDAO {

    /**
     * 获取菜单列表
     *
     * @param sysFunction
     * @return
     */
    public List<SysFunction> findSysFunctionList(SysFunction sysFunction);

    /**
     * 根据菜单ID，获取菜单
     *
     * @param functionId
     * @return
     */
    public SysFunction getFunctionById(Integer functionId);

    /**
     * 添加菜单
     *
     * @param sysFunction
     * @return
     */
    public boolean addFunction(SysFunction sysFunction);

    /**
     * 更新菜单
     *
     * @param sysFunction
     * @return
     */
    public boolean updateFunction(SysFunction sysFunction);

    /**
     * 删除菜单
     *
     * @param functionId
     * @return
     */
    public boolean deleteFunctionById(Integer functionId);

    /**
     * 获取子菜单列表
     *
     * @param parentId
     * @return
     */
    public List<SysFunction> findFunctionByParentId(Integer parentId);

    /**
     * 逻辑删除
     *
     * @param functionId
     * @return
     */
    public boolean logicalDeleteFunctionById(Integer functionId);
}
