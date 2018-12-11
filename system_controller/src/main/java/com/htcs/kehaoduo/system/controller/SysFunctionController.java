package com.htcs.kehaoduo.system.controller;

import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.system.bean.SysFunction;
import com.htcs.kehaoduo.system.service.SysFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dragon
 * @create 2017-08-01 9:13
 **/
@RestController
@RequestMapping("/admin/sysFunction")
public class SysFunctionController {

    @Autowired
    private SysFunctionService sysFunctionService;

    /**
     * 查询菜单列表
     *
     * @return 菜单集合
     */
    @GetMapping(value = "/functionListAll")
    public List<SysFunction> functionListAll() {
        return sysFunctionService.findFunctionlistAll();
    }

    /**
     * 通过functionId查询function
     *
     * @param functionId 菜单id
     * @return function实体
     */
    @GetMapping(value = "/getFunction")
    public SysFunction getFunction(@RequestParam("functionId") Integer functionId) {
        Preconditions.checkNotNull(functionId, "菜单id为空，请重新操作");
        return sysFunctionService.getFunctionById(functionId);
    }

    /**
     * 添加菜单
     *
     * @param sysFunction SysFunction实体
     * @return JSON数据状态
     */
    @PostMapping(value = "/addFunction")
    public ReturnStatus addFunction(SysFunction sysFunction) {
        Preconditions.checkNotNull(sysFunction, "缺少数据");
        sysFunctionService.addFunction(sysFunction);

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "添加成功");
    }

    /**
     * 修改菜单
     *
     * @param sysFunction SysFunction实体
     * @return JSON数据状态
     */
    @PostMapping(value = "/updateFunction")
    public ReturnStatus updateFunction(SysFunction sysFunction) {
        Preconditions.checkNotNull(sysFunction, "缺少数据");
        sysFunctionService.updateFunction(sysFunction);

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "更新成功");
    }

    /**
     * 根据functionId删除菜单
     *
     * @param functionId 菜单id
     * @return JSON数据状态
     */
    @DeleteMapping(value = "/delFunction")
    public ReturnStatus delFunction(@RequestParam(value = "functionId") Integer functionId) {
        Preconditions.checkNotNull(functionId, "菜单id为空，请重新操作");

        /*判断是否有子菜单*/
        List<SysFunction> child = sysFunctionService.findFunctionByParentId(functionId);
        Preconditions.checkArgument((child != null && child.size() == 0), "有子菜单，不能删除！");

        sysFunctionService.deleteFunctionById(functionId);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "删除成功");
    }

}
