package com.htcs.kehaoduo.system.service;

import com.htcs.kehaoduo.system.bean.SysFunction;

import java.util.List;

/**
 * @author zhaokaiyuan
 */
public interface SysFunctionService {

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
     * @param function
     * @return
     */
    public List<SysFunction> findFunctionByParentId(Integer function);

    /**
     * 逻辑删除
     *
     * @param function
     * @return
     */
    public boolean logicalDeleteFunctionById(Integer function);

    /**
     * 查询所有的列表
     *
     * @return
     */
    public List<SysFunction> findFunctionlistAll();

}
