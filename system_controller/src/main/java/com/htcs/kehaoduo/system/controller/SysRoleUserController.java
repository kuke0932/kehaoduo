package com.htcs.kehaoduo.system.controller;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.common.page.PageBean;
import com.htcs.kehaoduo.system.bean.SysRoleUserModel;
import com.htcs.kehaoduo.system.bean.SysUser;
import com.htcs.kehaoduo.system.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dragon
 * @create 2017-08-01 11:48
 **/
@RestController
@RequestMapping("/admin/sysRoleUser")
public class SysRoleUserController {

    @Autowired
    private SysRoleUserService roleUserService;

    /**
     * 根据角色查询用户
     *
     * @param roleUserModel 角色用户模型
     * @param currentPage   当前页
     * @param pageSize      每页显示条数
     * @return 分页实体
     */
    @GetMapping("/findSysUserListByRole")
    public ReturnStatus<PageBean<SysUser>> findSysUserListByRole(SysRoleUserModel roleUserModel, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        List<SysUser> userList = roleUserService.findSysUserListByRoleUser(roleUserModel);
        userList = userList == null ? new ArrayList<>() : userList;

        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "查询成功", new PageBean<>(userList));
    }

    /**
     * 给角色添加用户
     *
     * @param userIds 用户ids
     * @param roleId  角色id
     * @return JSON数据状态
     */
    @PostMapping("/addUserToRole")
    public ReturnStatus addUserToRole(String userIds, Integer roleId) {
        Preconditions.checkArgument((!Strings.isNullOrEmpty(userIds) || roleId != null), "缺少数据");
        roleUserService.addRoleUsers(userIds, roleId);

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "添加成功");
    }

    /**
     * 从角色删除用户
     *
     * @param userIds 用户ids
     * @param roleId  角色id
     * @return JSON数据状态
     */
    @PostMapping("/removeUserFromRole")
    public ReturnStatus removeUserFromRole(String userIds, Integer roleId) {
        Preconditions.checkArgument((!Strings.isNullOrEmpty(userIds) || roleId != null), "缺少数据");
        roleUserService.deleteRoleUsers(userIds, roleId);

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "删除成功");
    }


}
