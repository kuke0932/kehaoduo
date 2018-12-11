package com.htcs.kehaoduo.common.util;


import com.htcs.kehaoduo.common.CacheConstans;
import com.htcs.kehaoduo.common.model.LoginSysFunction;
import com.htcs.kehaoduo.common.model.LoginSysUser;
import com.htcs.kehaoduo.common.model.LoginSysUserMsg;
import com.htcs.kehaoduo.common.model.LoginWebUser;
import com.htcs.kehaoduo.common.util.token.JWTokenUtil;
import com.htcs.kehaoduo.common.util.token.VerifyResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.htcs.kehaoduo.common.CacheConstans.SYSTEM_CACHE;


public class SingletonLoginUtils {
    /**
     * 根据cookie获取用户
     *
     * @param request
     * @return
     */
    public static Optional<LoginWebUser> getLoginWebUserByCookie(HttpServletRequest request) {
        String userKey = WebUtils.getCookie(request, CacheConstans.WEB_USER_LOGIN_PREFIX);
        return getLoginWebUser(Optional.ofNullable(userKey));
    }

    /**
     * 根据cookie获取用户
     *
     * @param request
     * @return
     */
    public static Optional<LoginWebUser> getLoginWebUserByHeader(HttpServletRequest request) {
        String token = WebUtils.getHeader(request, CacheConstans.X_AUTH_TOKEN);
        VerifyResult verify = JWTokenUtil.verify(token);
        return getLoginWebUser(Optional.ofNullable(token));
    }

    /**
     * 从缓存获取前台登陆用户
     *
     * @param userKey
     * @return
     */
    public static Optional<LoginWebUser> getLoginWebUser(Optional<String> userKey) {
        return userKey.map((u) -> (LoginWebUser) CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(CacheConstans.WEB_CACHE, u));
    }

    /**
     * 从缓存获取前台登陆用户
     *
     * @param userKey
     * @return
     */
    public static Optional<LoginWebUser> getLoginWebUser(String userKey) {
        return getLoginWebUser(Optional.ofNullable(userKey));
    }

    /**
     * 获取后台登录用户
     *
     * @return SysUser
     * @throws Exception
     */
    public static Optional<LoginSysUser> getLoginSysUserByCookie(HttpServletRequest request) {
        String userKey = WebUtils.getCookie(request, CacheConstans.SYS_USER_LOGIN_PREFIX);
        return getLoginSysUser(Optional.ofNullable(userKey));
    }

    public static Optional<LoginSysUser> getLoginSysUserByHeader(HttpServletRequest request) {
        String token = WebUtils.getHeader(request, CacheConstans.X_AUTH_TOKEN);
        VerifyResult<Jws<Claims>> verify = JWTokenUtil.verify(token);
        String userKey = verify.getData().getBody().getSubject();
        request.setAttribute("verifyResult", verify);   //将校验结果存入request中
        return getLoginSysUser(Optional.ofNullable(userKey));
    }

    /**
     * 获取后台登录用户
     *
     * @return SysUser
     * @throws Exception
     */
    public static Optional<LoginSysUser> getLoginSysUser(Optional<String> userKey) {
        return userKey.map(u -> (LoginSysUserMsg) CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(SYSTEM_CACHE, u)).map(u -> u.getUser());
    }

    public static Optional<LoginSysUser> getLoginSysUser(String userKey) {
        return getLoginSysUser(Optional.ofNullable(userKey));
    }

    /**
     * 获取后台登录的信息
     *
     * @return SysUser
     */
    public static LoginSysUserMsg getLoginMsgByCookie(HttpServletRequest request) {
        String userKey = WebUtils.getCookie(request, CacheConstans.SYS_USER_LOGIN_PREFIX);
        if (!StringUtils.isEmpty(userKey)) {
            LoginSysUserMsg loginMsg = (LoginSysUserMsg) CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(SYSTEM_CACHE, userKey);
            if (loginMsg != null) {
                return loginMsg;
            }
        }
        return null;
    }

    public static LoginSysUserMsg getLoginMsgByHeader(HttpServletRequest request) {
        String token = WebUtils.getHeader(request, CacheConstans.X_AUTH_TOKEN);
        VerifyResult<Jws<Claims>> verify = JWTokenUtil.verify(token);
        String userKey = verify.getData().getBody().getSubject();
        if (!StringUtils.isEmpty(userKey)) {
            LoginSysUserMsg loginMsg = (LoginSysUserMsg) CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(SYSTEM_CACHE, userKey);
            if (loginMsg != null) {
                return loginMsg;
            }
        }
        return null;
    }


    public static LoginSysFunction getLoginFunctionTreeByCookie(HttpServletRequest request, Integer rootId) {
        LoginSysFunction functionTree = getLoginMsgByCookie(request).getFunctionTree();
        if (functionTree == null) {
            List<LoginSysFunction> functionList = getLoginMsgByCookie(request).getFunctionList();
            functionTree = initFunctionTree(functionList, rootId);
            getLoginMsgByCookie(request).setFunctionTree(functionTree);
        }
        return functionTree;
    }

    public static LoginSysFunction getLoginFunctionTreeByHeader(HttpServletRequest request, Integer rootId) {

        LoginSysFunction functionTree = getLoginMsgByHeader(request).getFunctionTree();
        if (functionTree == null) {
            List<LoginSysFunction> functionList = getLoginMsgByHeader(request).getFunctionList();
            functionTree = initFunctionTree(functionList, rootId);
            getLoginMsgByHeader(request).setFunctionTree(functionTree);
        }
        return functionTree;
    }

    /**
     * 获取子列表
     *
     * @param function
     * @param functionList
     */
    private static void buildhildren(LoginSysFunction function, List<LoginSysFunction> functionList) {
        for (LoginSysFunction child : functionList) {
            if (child != null && Objects.equals(child.getParentId(), function.getId())) {
                if (function.getChildrenList() == null) {
                    function.setChildrenList(new ArrayList<LoginSysFunction>());
                }
                function.getChildrenList().add(child);
            }
        }
    }

    /**
     * 构造角色树
     *
     * @param functionList
     */
    private static void createTree(List<LoginSysFunction> functionList) {
        for (LoginSysFunction function : functionList) {
            buildhildren(function, functionList);
        }
    }

    /**
     * 构建菜单树
     *
     * @param functionList
     * @param functionTreeRootId
     * @return
     */
    public static LoginSysFunction initFunctionTree(List<LoginSysFunction> functionList, Integer functionTreeRootId) {
        //构造菜单树
        createTree(functionList);
        LoginSysFunction functionTree = null;
        for (LoginSysFunction function : functionList) {
            if (function != null && Objects.equals(function.getId(), functionTreeRootId)) {
                functionTree = function;
                break;
            }
        }
        return functionTree;
    }
}
