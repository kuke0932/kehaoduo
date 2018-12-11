package com.htcs.kehaoduo.system.controller;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.common.Common;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.common.page.PageBean;
import com.htcs.kehaoduo.system.bean.SysRoleUserModel;
import com.htcs.kehaoduo.system.bean.SysUser;
import com.htcs.kehaoduo.system.bean.SysUserModel;
import com.htcs.kehaoduo.system.service.SysRoleUserService;
import com.htcs.kehaoduo.system.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dragon
 * @create 2017-08-01 15:28
 **/
@RestController
@RequestMapping("/admin/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService service;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    /**
     * 查询用户列表
     *
     * @param sysUserModel 后台系统用户实体
     * @param currentPage  当前页
     * @param pageSize     每页显示条数
     * @return 分页实体
     */
    @GetMapping("/list")
    public ReturnStatus<PageBean<SysUser>> list(SysUserModel sysUserModel, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        sysUserModel.setLogicalDel(Common.LOGICALDEL_NO);
        List<SysUser> userList = service.findSysUserList(sysUserModel);
        userList = userList == null ? new ArrayList<>() : userList;

        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "查询成功", new PageBean<>(userList));
    }

    /**
     * 查询用户列表
     *
     * @param sysRoleUserModel 后台系统用户实体
     * @param currentPage      当前页
     * @param pageSize         每页显示条数
     * @return 分页实体
     */
    @GetMapping("/listByRoleId")
    public ReturnStatus<PageBean<SysUser>> listByRoleId(SysRoleUserModel sysRoleUserModel, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        sysRoleUserModel.setLogicalDel(0);
        List<SysUser> userList = sysRoleUserService.listByRoleId(sysRoleUserModel);
        userList = userList == null ? new ArrayList<>() : userList;

        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "查询成功", new PageBean<>(userList));
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return SysUser实体
     */
    @GetMapping("/getUser")
    public SysUser getUser(Integer userId) {
        Preconditions.checkNotNull(userId, "缺少数据");
        return service.getUserById(userId);
    }

    /**
     * 添加用户
     *
     * @param sysUser SysUser实体
     * @return JSON数据状态
     */
    @PostMapping("/addUser")
    public ReturnStatus addUser(SysUser sysUser) {
        Preconditions.checkNotNull(sysUser, "缺少数据");
        boolean flag = service.addUser(sysUser);
        if (flag) {
            return new ReturnStatus(ReturnStatus.StatusCode.OK, "添加成功");
        } else {
            return new ReturnStatus(ReturnStatus.StatusCode.BAD_REQUEST, "该用于已经添加，请勿重复添加！");
        }
    }

    /**
     * 修改用户
     *
     * @param sysUser SysUser实体
     * @return JSON数据状态
     */
    @PostMapping("/updateUser")
    public ReturnStatus updateUser(SysUser sysUser) {
        Preconditions.checkNotNull(sysUser, "缺少数据");
        service.updateUser(sysUser);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "更新成功");
    }

    /**
     * 重置密码
     *
     * @param userId
     * @param password
     * @param confirmPassword
     * @return
     */
    @PostMapping("/resetPassword")
    public ReturnStatus resetPassword(Integer userId, String password, String confirmPassword) {
        Preconditions.checkNotNull(userId, "用户id不能为空，请重新输入！");
        Preconditions.checkArgument(StringUtils.isNotEmpty(password), "新密码不能为空，请重新输入!");
        Preconditions.checkArgument(StringUtils.isNotEmpty(confirmPassword), "确认密码不能为空，请重新输入!");
        Preconditions.checkArgument(password.equals(confirmPassword), "确认密码不能为空，请重新输入!");

        SysUser sysUser = service.getUserById(userId);
        String salt = sysUser.getSalt();
        String newPassword = DigestUtils.md5Hex(salt + password);
        sysUser.setLoginPwd(newPassword);
        service.updateUser(sysUser);
        return new ReturnStatus("密码更新成功");
    }

    /**
     * 根据用户Id删除用户
     *
     * @param ids 用户ids
     * @return JSON数据状态
     */
    @DeleteMapping("/delUser")
    public ReturnStatus delUser(String ids) {
        Preconditions.checkNotNull(ids, "缺少数据");
        String[] strings = ids.split(",");
        for (String id : strings) {
            service.deleteUserById(Integer.valueOf(id));
        }
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "删除成功");
    }
}
