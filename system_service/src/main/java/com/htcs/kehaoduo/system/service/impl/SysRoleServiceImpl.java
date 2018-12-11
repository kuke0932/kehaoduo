package com.htcs.kehaoduo.system.service.impl;

import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.dao.SysRoleDAO;
import com.htcs.kehaoduo.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
/**
 * @author zhaokaiyuan
 */
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired(required = false)
    private SysRoleDAO roleDao;

    /**
     * 获取子列表
     *
     * @param role
     * @param roleList
     */
    public void buildhildren(SysRole role, List<SysRole> roleList) {
        for (SysRole childrenRole : roleList) {
            if (childrenRole != null && Objects.equals(childrenRole.getParentId(), role.getId())) {
                if (role.getChildrenList() == null) {
                    role.setChildrenList(new ArrayList<SysRole>());
                }
                role.getChildrenList().add(childrenRole);
            }
        }
    }

    /**
     * 构造角色树
     *
     * @param roleList
     */
    private void createTree(List<SysRole> roleList) {
        for (SysRole role : roleList) {
            buildhildren(role, roleList);
        }
    }

    /*
     * 获取角色列表
     */
    @Override
    public SysRole findRoleTree(Integer rootId) {
        //获取角色的列表
        List<SysRole> roleList = roleDao.findSysRoleList(new SysRole());
        //构造角色树
        createTree(roleList);
        SysRole roleTree = null;
        for (SysRole role : roleList) {
            if (Objects.equals(role.getId(), rootId)) {
                roleTree = role;
                break;
            }
        }
        return roleTree;
    }

    /**
     * 根据用户ID，获取用户信息
     */
    @Override
    public SysRole getRoleById(Integer roleId) {
        // TODO Auto-generated method stub
        return roleDao.getRoleById(roleId);
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
        return roleDao.addRole(roleModel);
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
        return roleDao.updateRole(roleModel);
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
        return roleDao.deleteRoleById(roleId);
    }


    /**
     * 获取子角色列表
     */
    @Override
    public List<SysRole> findRoleByParentId(Integer roleId) {
        // TODO Auto-generated method stub
        return roleDao.findRoleByParentId(roleId);
    }

    /**
     * 逻辑删除
     */
    @Override
    public boolean logicalDeleteRoleById(Integer roleId) {

        return roleDao.logicalDeleteRoleById(roleId);
    }

    @Override
    public List<SysRole> findRolelistAll() {
        // TODO Auto-generated method stub
        return roleDao.findSysRoleList(new SysRole());
    }


}
