package com.htcs.kehaoduo.system.controller;


import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dragon
 * @create 2017-08-01 10:05
 **/
@RestController
@RequestMapping("/admin/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService service;

    /**
     * 获取角色树
     *
     * @return 角色集合
     */
    @GetMapping(value = "/roleTree")
    public List<SysRole> roleTree() {
        SysRole roleTree = service.findRoleTree(1);
        List<SysRole> list = new ArrayList<>();
        list.add(roleTree);
        return list;
    }

    /**
     * 获取所有角色
     *
     * @return 角色集合
     */
    @GetMapping(value = "roleListAll")
    public List<SysRole> roleListAll() {
        return service.findRolelistAll();
    }

    /**
     * 根据角色id查询角色信息
     *
     * @param roleId 角色id
     * @return
     */
    @GetMapping(value = "/getRole")
    public SysRole getRole(@RequestParam(value = "roleId") Integer roleId) {
        Preconditions.checkNotNull(roleId, "角色id为空，请重新操作");
        return service.getRoleById(roleId);
    }

    /**
     * 添加角色信息
     *
     * @param sysRole SysRole实体
     * @return JSON数据状态
     */
    @PostMapping(value = "/addRole")
    public ReturnStatus addRole(SysRole sysRole) {
        Preconditions.checkNotNull(sysRole, "缺少数据");
        service.addRole(sysRole);

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "添加成功");
    }

    /**
     * 修改角色信息
     *
     * @param sysRole SysRole实体
     * @return JSON数据状态
     */
    @PostMapping(value = "/updateRole")
    public ReturnStatus updateRole(SysRole sysRole) {
        Preconditions.checkNotNull(sysRole, "缺少数据");
        service.updateRole(sysRole);

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "更新成功");
    }

    /**
     * 根据roleId删除角色
     *
     * @param roleId 角色id
     * @return JSON数据状态
     */
    @DeleteMapping("/delRole")
    public ReturnStatus delRole(@RequestParam(value = "roleId") Integer roleId) {
        Preconditions.checkNotNull(roleId, "角色id为空，请重新操作");

        /*判断是否有子角色*/
        List<SysRole> child = service.findRoleByParentId(roleId);
        Preconditions.checkArgument((child != null && child.size() == 0), "有子角色，不能删除！");

        service.deleteRoleById(roleId);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "删除成功");
    }

}
