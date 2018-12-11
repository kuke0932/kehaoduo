package com.htcs.kehaoduo.system.service.impl;

import com.htcs.kehaoduo.common.CacheConstans;
import com.htcs.kehaoduo.common.util.CacheUtil;
import com.htcs.kehaoduo.system.bean.SysFunction;
import com.htcs.kehaoduo.system.dao.SysFunctionDAO;
import com.htcs.kehaoduo.system.service.SysFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
/**
 * @author zhaokaiyuan
 */
public class SysFunctionServiceImpl implements SysFunctionService {
    @Autowired
    private SysFunctionDAO sysFunctionDAO;

    /**
     * 根据用户ID，获取菜单信息
     */
    @Override
    public SysFunction getFunctionById(Integer functionId) {
        // TODO Auto-generated method stub
        return sysFunctionDAO.getFunctionById(functionId);
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
        //删除缓存
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).remove(CacheConstans.SYSTEM_CACHE, CacheConstans.SYS_ALL_USER_FUNCTION_PREFIX);
        return sysFunctionDAO.addFunction(sysFunction);
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
        //删除缓存
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).remove(CacheConstans.SYSTEM_CACHE, CacheConstans.SYS_ALL_USER_FUNCTION_PREFIX);
        return sysFunctionDAO.updateFunction(sysFunction);
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
        //删除缓存
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).remove(CacheConstans.SYSTEM_CACHE, CacheConstans.SYS_ALL_USER_FUNCTION_PREFIX);
        return sysFunctionDAO.deleteFunctionById(functionId);
    }


    /**
     * 获取子菜单列表
     */
    @Override
    public List<SysFunction> findFunctionByParentId(Integer parentId) {
        // TODO Auto-generated method stub
        return sysFunctionDAO.findFunctionByParentId(parentId);
    }

    /**
     * 逻辑删除
     */
    @Override
    public boolean logicalDeleteFunctionById(Integer functionId) {
        //删除缓存
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).remove(CacheConstans.SYSTEM_CACHE, CacheConstans.SYS_ALL_USER_FUNCTION_PREFIX);
        return sysFunctionDAO.logicalDeleteFunctionById(functionId);
    }

    /**
     * 获取菜单列表
     */
    @Override
    public List<SysFunction> findFunctionlistAll() {
        // TODO Auto-generated method stub
        return sysFunctionDAO.findSysFunctionList(new SysFunction());
    }

}
