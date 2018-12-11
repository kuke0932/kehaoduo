package com.htcs.kehaoduo.system.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.system.bean.SysRoleFunction;
import com.htcs.kehaoduo.system.service.SysRoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dragon
 * @create 2017-08-01 11:26
 **/
@RestController
@RequestMapping("/admin/sysRoleFunction")
public class SysRoleFunctionController {

    @Autowired
    private SysRoleFunctionService roleFunctionService;

    /**
     * 根据角色id查询角色菜单关联信息
     *
     * @param roleId 角色id
     * @return SysRoleFunction集合
     */
    @GetMapping(value = "/findSysRoleFunctionListByRoleId")
    public List<SysRoleFunction> findSysRoleFunctionListByRoleId(@RequestParam(value = "roleId") Integer roleId) {
        Preconditions.checkNotNull(roleId, "角色id为空，请重新操作");

        return roleFunctionService.findRoleFunctionListByRoleId(roleId);
    }

    /**
     * 给角色授权
     *
     * @param functionIds 菜单ids
     * @param roleId      角色id
     * @return JSON数据状态
     */
    @PostMapping(value = "/setRoleFunctions")
    public ReturnStatus setRoleFunctions(@RequestParam(value = "functionIds") String functionIds,
                                         @RequestParam(value = "roleId") Integer roleId) {
        Preconditions.checkArgument((!Strings.isNullOrEmpty(functionIds) || roleId != null), "缺少数据");
        roleFunctionService.setRoleFunctions(functionIds, roleId);

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "授权成功");
    }


}
