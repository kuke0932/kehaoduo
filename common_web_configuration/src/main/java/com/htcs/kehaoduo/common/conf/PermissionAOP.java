package com.htcs.kehaoduo.common.conf;


import com.htcs.kehaoduo.common.annotations.HasRole;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.common.exception.ReturnStatusException;
import com.htcs.kehaoduo.common.model.LoginSysRole;
import com.htcs.kehaoduo.common.model.LoginSysUser;
import com.htcs.kehaoduo.common.model.LoginSysUserMsg;
import com.htcs.kehaoduo.common.util.CacheUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

import static com.htcs.kehaoduo.common.CacheConstans.SYSTEM_CACHE;

@Aspect
@Component
public class PermissionAOP {

    private static Logger logger = LoggerFactory.getLogger(PermissionAOP.class);

    @Pointcut("@annotation(com.htcs.kehaoduo.common.annotations.HasRole)")
    public void hasRole() {
        logger.debug(" has role");
    }


    @Around("hasRole()")
    public void beforeExec(ProceedingJoinPoint pjp) throws Throwable {
        LoginSysUser loginSysUser = RequestUserHolder.currentSysUser();
        if(loginSysUser == null) {
            throw new ReturnStatusException(ReturnStatus.StatusCode.UNAUTHORIZED, "签名错误，请重新获取签名！");
        }

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        HasRole annotation = method.getAnnotation(HasRole.class);
        String[] roleValue = annotation.value();
        HasRole.Type type = annotation.type();

        LoginSysUserMsg loginMsg = (LoginSysUserMsg) CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(SYSTEM_CACHE, loginSysUser.getLoginName() + ".sys");
        List<LoginSysRole> roleList = loginMsg.getRoleList();

        boolean hasRole = true;
        LoginSysRole role = new LoginSysRole();
        for (int i = 0; i < roleValue.length; i++) {
            role.setRoleValue(roleValue[i]);
            if (!roleList.contains(role)) {
                hasRole = false;
                if (type.equals(HasRole.Type.AND)) {
                    break;
                }
            } else {
                if (type.equals(HasRole.Type.OR)) {
                    break;
                }
            }
        }
        if (hasRole) {
            pjp.proceed();
        } else {
            throw new ReturnStatusException(ReturnStatus.StatusCode.FORBIDDEN, "对不起，您没有权限访问！");
        }

    }

}
