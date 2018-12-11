package com.htcs.kehaoduo.common.conf;


import com.htcs.kehaoduo.common.model.LoginSysUser;
import com.htcs.kehaoduo.common.model.LoginWebUser;
import com.htcs.kehaoduo.common.util.SingletonLoginUtils;
import com.htcs.kehaoduo.common.util.token.JWTokenUtil;
import com.htcs.kehaoduo.common.util.token.VerifyResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.htcs.kehaoduo.common.CacheConstans.X_AUTH_TOKEN;


public class RequestUserListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent requestEvent) {
        if (!(requestEvent.getServletRequest() instanceof HttpServletRequest)) {
            throw new IllegalArgumentException("Request is not an HttpServletRequest: " + requestEvent.getServletRequest());
        } else {
            RequestUserHolder.resetThreadLocal();
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent requestEvent) {
        if (!(requestEvent.getServletRequest() instanceof HttpServletRequest)) {
            throw new IllegalArgumentException("Request is not an HttpServletRequest: " + requestEvent.getServletRequest());
        } else {
            HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
            String token = request.getHeader(X_AUTH_TOKEN);
            if(StringUtils.isEmpty(token) || "null".equals(token)) {
                token = request.getParameter("x");
            }
            if (StringUtils.isNotEmpty(token) && !"null".equals(token)) {
                VerifyResult<Jws<Claims>> verify = JWTokenUtil.verify(token);
                Jws<Claims> data = verify.getData();
                Optional<String> sub = Optional.ofNullable(data.getBody().getSubject());
                sub.ifPresent(s -> {
                    String sysOrWeb = s.substring(s.length() - 3);
                    if ("sys".equals(sysOrWeb)) {
                        Optional<LoginSysUser> loginSysUser = SingletonLoginUtils.getLoginSysUser(s);
                        loginSysUser.ifPresent(sysUser -> {
                            RequestUserHolder.setSysUser(sysUser);
                        });
                    } else if ("web".equals(sysOrWeb)) {
                        Optional<LoginWebUser> loginWebUser = SingletonLoginUtils.getLoginWebUser(s);
                        loginWebUser.ifPresent(webUser -> {
                            RequestUserHolder.setWebUser(webUser);
                        });
                    }

                });


            }
        }
    }
}