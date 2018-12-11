package com.htcs.kehaoduo.system.service.impl;

import com.htcs.kehaoduo.system.bean.SysFunction;
import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.bean.SysRoleFunction;
import com.htcs.kehaoduo.system.dao.SysRoleFunctionDAO;
import com.htcs.kehaoduo.system.service.SysRoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * @author zhaokaiyuan
 */
public class SysRoleFunctionServiceImpl implements SysRoleFunctionService {
    @Autowired(required = false)
    private SysRoleFunctionDAO roleFunctionDAO;

    /**
     * 根据角色ID获取用户列表
     *
     * @param user
     * @return
     */
    @Override
    public List<SysRoleFunction> findRoleFunctionListByRoleId(Integer roleId) {
        SysRoleFunction roleFunction = new SysRoleFunction();
        roleFunction.setRoleId(roleId);
        return roleFunctionDAO.findRoleFunctionListByRoleId(roleFunction);
    }

    /**
     * 添加用户角色关系
     *
     * @param sysRoleFunction
     * @return
     */
    @Override
    public boolean addRoleFunction(SysRoleFunction sysRoleFunction) {
        return roleFunctionDAO.addRoleFunction(sysRoleFunction);
    }

    /**
     * 删除用户角色关系
     *
     * @param roleId
     * @return
     */
    @Override
    public boolean deleteRoleFunctionByRoleId(Integer roleId) {
        SysRoleFunction roleFunction = new SysRoleFunction();
        roleFunction.setRoleId(roleId);
        return roleFunctionDAO.deleteRoleFunctionByRoleId(roleFunction);
    }

    @Override
    public boolean addRoleFunctions(String funtionIds, Integer roleId) {
        String[] functionIdArray = funtionIds.split(",");
        SysRoleFunction roleUser = new SysRoleFunction();
        for (String funtionId : functionIdArray) {
            roleUser.setFunctionId(Integer.valueOf(funtionId));
            roleUser.setRoleId(roleId);
            addRoleFunction(roleUser);
        }
        return true;
    }

    /**
     * 授权
     */
    @Override
    public boolean setRoleFunctions(String functionIds, Integer roleId) {
        deleteRoleFunctionByRoleId(roleId);
        addRoleFunctions(functionIds, roleId);
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
        return roleFunctionDAO.findFunctionListByRoleList(roleList);
    }

}
