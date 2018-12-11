package com.htcs.kehaoduo.common.interceptor;

import com.htcs.kehaoduo.common.CacheConstans;
import com.htcs.kehaoduo.system.dao.SysUserDAO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by htcs on 2017/7/28.
 */
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {

    //@Autowired
    //@Qualifier("adminInterceptorService")
    //RmiProxyFactoryBean rmiProxyFactoryBean;

    SysUserDAO sysUserDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(CacheConstans.X_AUTH_TOKEN);
        //RequestContextHolder.currentRequestAttributes();
        //Preconditions.checkNotNull(adminInterceptorInterface,  "请检查system服务是否开启！");


//        VerifyResult<Jws<Claims>> verify = JWTokenUtil.verify(token);
//        String userKey = verify.getData().getBody().getSubject();
//        Optional<LoginSysUser> loginSysUser = SingletonLoginUtils.getLoginSysUser(userKey);
//
//        sysUserDAO.getUserById(1);
//        return adminInterceptorInterface.preHandle(token);
        return true;
    }
}
