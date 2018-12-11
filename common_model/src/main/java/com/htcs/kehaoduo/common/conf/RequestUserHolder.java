package com.htcs.kehaoduo.common.conf;


import com.htcs.kehaoduo.common.model.LoginSysUser;
import com.htcs.kehaoduo.common.model.LoginWebUser;
import org.springframework.core.NamedThreadLocal;

import java.util.Map;

public class RequestUserHolder {

    private static final ThreadLocal<LoginSysUser> sysUserThreadLocalHoler = new NamedThreadLocal<>("sysUserThreadLocal");
    private static final ThreadLocal<LoginWebUser> webUserThreadLocalHolder = new NamedThreadLocal<>("webUserThreadLocal");
    private static final ThreadLocal<Map> dataInRequest = new NamedThreadLocal<>("dataInRequest");


    public static void setSysUser(LoginSysUser loginSysUser) {
        sysUserThreadLocalHoler.set(loginSysUser);
    }

    public static LoginSysUser currentSysUser() {
        return sysUserThreadLocalHoler.get();
    }

    public static void setWebUser(LoginWebUser loginWebUser) {
        webUserThreadLocalHolder.set(loginWebUser);
    }

    public static Map getDataInRequest() {
        return dataInRequest.get();
    }

    public static void setDataInRequest(Map map) {
        dataInRequest.set(map);
    }

    public static LoginWebUser currentWebUser() {
        return webUserThreadLocalHolder.get();
    }

    public static void resetThreadLocal() {
        sysUserThreadLocalHoler.remove();
        webUserThreadLocalHolder.remove();
        dataInRequest.remove();
    }
}