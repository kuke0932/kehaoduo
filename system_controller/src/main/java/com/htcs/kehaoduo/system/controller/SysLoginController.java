package com.htcs.kehaoduo.system.controller;

import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.common.CacheConstans;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.common.conf.RequestUserHolder;
import com.htcs.kehaoduo.common.model.LoginSysFunction;
import com.htcs.kehaoduo.common.model.LoginSysUser;
import com.htcs.kehaoduo.common.model.LoginSysUserMsg;
import com.htcs.kehaoduo.common.util.CacheUtil;
import com.htcs.kehaoduo.common.util.SingletonLoginUtils;
import com.htcs.kehaoduo.common.util.token.JWTokenUtil;
import com.htcs.kehaoduo.common.util.token.VerifyResult;
import com.htcs.kehaoduo.system.bean.SysFunction;
import com.htcs.kehaoduo.system.bean.SysRole;
import com.htcs.kehaoduo.system.bean.SysUser;
import com.htcs.kehaoduo.system.service.SysRoleFunctionService;
import com.htcs.kehaoduo.system.service.SysRoleUserService;
import com.htcs.kehaoduo.system.service.SysUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
/**
 * @author zhaokaiyuan
 */
public class SysLoginController {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleUserService sysRoleUserService;
    @Autowired
    SysRoleFunctionService sysRoleFunctionService;
    @Value("${rsa.private-key}")
    private String privateKey;

    @RequestMapping("/login")
    public ReturnStatus login(String username, String password) throws Exception {

        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String username1 = req.getParameter("username");
        System.out.println(username1);

        Preconditions.checkArgument(StringUtils.isNotEmpty(username), "登录名不能为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(password), "密码不能为空");

        SysUser sysUser = sysUserService.getUserByLoginName(username);
        Preconditions.checkNotNull(sysUser, "用户名不存在，请注册！");

        String salt = sysUser.getSalt();
        String decryptPassWord = password; // RSA.decrypt(password, privateKey);
        String hashPassword = DigestUtils.md5Hex(salt + decryptPassWord);
        Preconditions.checkArgument(hashPassword.equals(sysUser.getLoginPwd()), "用户名或密码错误，请重新输入！");

        String userKey = username + ".sys";
        String token = JWTokenUtil.createToken(userKey);

        List<SysRole> roleListByUserId = sysRoleUserService.findRoleListByUserId(sysUser.getId());
        Preconditions.checkArgument(roleListByUserId.size() != 0, "没有权限！");
        List<SysFunction> functionListByRoleList = new ArrayList();
        if (!org.springframework.util.ObjectUtils.isEmpty(roleListByUserId)) {
            functionListByRoleList = sysRoleFunctionService.findFunctionListByRoleList(roleListByUserId);
        } else {

        }

        LoginSysUserMsg loginSysUserMsg = new LoginSysUserMsg();
        loginSysUserMsg.setUser(sysUser.copyOf());
        loginSysUserMsg.setRoleList(roleListByUserId.stream().map(r -> r.copyOf()).collect(Collectors.toList()));
        loginSysUserMsg.setFunctionList(functionListByRoleList.stream().map(f -> f.copyOf()).collect(Collectors.toList()));

        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).put(CacheConstans.SYSTEM_CACHE, userKey, loginSysUserMsg, 60, TimeUnit.MINUTES);

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "登录成功！", token);
    }


    @PostMapping("/logout")
    public ReturnStatus logout() {
        LoginSysUser loginSysUser = RequestUserHolder.currentSysUser();
        String userKey = loginSysUser.getLoginName() + ".sys";
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).remove(CacheConstans.SYSTEM_CACHE, userKey);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "");

    }

    /**
     * 获取登陆信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/getLoginMsg")
    public ReturnStatus getLoginMsg(HttpServletRequest request) {
        VerifyResult<Jws<Claims>> verifyResult = (VerifyResult<Jws<Claims>>) request.getAttribute("verifyResult");
        String userKey = verifyResult.getData().getBody().getSubject();
        LoginSysUserMsg o = (LoginSysUserMsg) CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(CacheConstans.SYSTEM_CACHE, userKey);

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "ok", o);
    }

    @PostMapping("/functionListTree")
    public LoginSysFunction functionListTree(HttpServletRequest request) {
        LoginSysFunction loginSysFunction = SingletonLoginUtils.getLoginFunctionTreeByHeader(request, 0);
        return loginSysFunction;
    }

    @GetMapping("/getUserInfo")
    public ReturnStatus getLoginMsg() {
        LoginSysUser loginSysUser = RequestUserHolder.currentSysUser();
        HashMap map = new HashMap();

        String userKey =  loginSysUser.getLoginName()+".sys";
        LoginSysUserMsg o = (LoginSysUserMsg) CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(CacheConstans.SYSTEM_CACHE, userKey);

        map.put("id", loginSysUser.getId());
        map.put("name", loginSysUser.getLoginName());
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("introduction", loginSysUser.getUserName());
        map.put("role", o.getRoleList().stream().map(m -> m.getRoleValue()).collect(Collectors.toList()));
        return new ReturnStatus(map);
    }
}
