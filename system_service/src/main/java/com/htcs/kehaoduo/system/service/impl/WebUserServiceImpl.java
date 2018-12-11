package com.htcs.kehaoduo.system.service.impl;

import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.common.conf.RequestUserHolder;
import com.htcs.kehaoduo.common.model.LoginSysUser;
import com.htcs.kehaoduo.common.model.LoginWebUser;
import com.htcs.kehaoduo.common.util.CacheUtil;
import com.htcs.kehaoduo.common.util.MD5Util;
import com.htcs.kehaoduo.common.util.RandomStrUtils;
import com.htcs.kehaoduo.system.bean.WebUser;
import com.htcs.kehaoduo.system.bean.WebUserCheckLog;
import com.htcs.kehaoduo.system.dao.WebUserDAO;
import com.htcs.kehaoduo.system.dto.AdvertiserDto;
import com.htcs.kehaoduo.system.mapper.WebUserCheckLogMapper;
import com.htcs.kehaoduo.system.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhaokaiyuan
 * @create 2017-10-20 13:52
 **/
@Service
@Transactional
public class WebUserServiceImpl implements WebUserService {

    @Autowired
    WebUserDAO webUserDao;

    @Autowired
    WebUserCheckLogMapper webUserCheckLogMapper;

    @Override
    public WebUser getWebUserById(Integer id) {
        return webUserDao.getWebUserById(id);
    }

    @Override
    public WebUser getWebUserByLoginName(String loginName) {
        return webUserDao.getWebUserByLoginName(loginName);
    }

    @Override
    public WebUser getWebUserByMobile(String mobile) {
        return webUserDao.getWebUserByMobile(mobile);
    }

    @Override
    public WebUser getWebUserByOpenidA(String openId) {
        return webUserDao.getWebUserByOpenidA(openId);
    }

    @Override
    public List<WebUser> findWebUserList(WebUser webUser) {
        return webUserDao.findWebUserList(webUser);
    }

    @Override
    public Integer addWebUser(WebUser webUser) {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();

        String salt = RandomStrUtils.generateStr(6);
        webUser.setSalt(salt);
        webUser.setLoginPwd(MD5Util.MD5Encode(salt + webUser.getLoginPwd()));
        if (loginWebUser != null) {
            webUser.setCreateBy(loginWebUser.getId());
            webUser.setCreateName(loginWebUser.getUserName());
        }
        webUser.setCreateTime(new Date());
        int i = webUserDao.addWebUser(webUser);
        if (i > 0) {
            webUser.setWebUserId(webUser.getId());
            webUserDao.addWebUserExt(webUser);
            webUser.setId(webUser.getWebUserId());
        }
        return i;
    }

    @Override
    public Integer updateWebUser(WebUser webUser) {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();

        if (loginWebUser != null) {
            webUser.setUpdateBy(loginWebUser.getId());
            webUser.setUpdateName(loginWebUser.getUserName());
        }
        webUser.setUpdateTime(new Date());
        int i = webUserDao.updateWebUser(webUser);
        int j = i > 0 ? webUserDao.updateWebUserExt(webUser) : 0;
        return i;
    }

    @Override
    public Integer logicalDeleteWebUserById(Integer id) {
        return webUserDao.logicalDeleteWebUserById(id);
    }

    @Override
    public Integer deleteWebUserById(Integer id) {
        return webUserDao.deleteWebUserById(id);
    }

    @Override
    public boolean isRegistered(String mobile) {
        WebUser webUserByMobile = webUserDao.getWebUserByMobile(mobile);
        return webUserByMobile != null ? true : false;
    }

    @Override
    public Integer updateMobileById(Integer id, String mobile) {
        WebUser webUser = new WebUser();
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();

        if (loginWebUser != null) {
            webUser.setUpdateBy(loginWebUser.getId());
            webUser.setUpdateName(loginWebUser.getUserName());
        }
        webUser.setUpdateTime(new Date());
        webUser.setId(id);
        webUser.setMobile(mobile);
        webUser.setLoginName(mobile);
        webUser.setWebUserId(id);
        int i = webUserDao.updateWebUser(webUser);
        int j = i > 0 ? webUserDao.updateWebUserExt(webUser) : 0;
        loginWebUser.setMobile(mobile);
        loginWebUser.setLoginName(mobile);
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).put(id + ".web", loginWebUser);
        return i;

    }

    @Override
    public Integer updatePasswordById(Integer id, String pwd) {

        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();

        WebUser updateWebUser = new WebUser();
        updateWebUser.setId(id);

        if (loginWebUser != null) {
            updateWebUser.setUpdateBy(loginWebUser.getId());
            updateWebUser.setUpdateName(loginWebUser.getUserName());
        }
        updateWebUser.setUpdateTime(new Date());

        String salt = RandomStrUtils.generateStr(6);
        updateWebUser.setSalt(salt);

        updateWebUser.setLoginPwd(MD5Util.MD5Encode(salt + pwd));
        updateWebUser.setWebUserId(id);
        int i = webUserDao.updateWebUser(updateWebUser);
        int j = i > 0 ? webUserDao.updateWebUserExt(updateWebUser) : 0;
        return i;
    }

    @Override
    public WebUser getWebUserByOpenidB(String openId) {
        return webUserDao.getWebUserByOpenidB(openId);
    }

    @Override
    public List<Map<String, Object>> listAdvertiser(AdvertiserDto advertiserDto) {
        LoginSysUser sysUser = RequestUserHolder.currentSysUser();
        Preconditions.checkNotNull(sysUser, "当前用户未登录或登录超时，请重新登录！");
        advertiserDto.setSysUserId(sysUser.getId());
        return webUserDao.listAdvertiser(advertiserDto);
    }


    @Override
    public List<Map<String, Object>> listAdvertiser4Check(AdvertiserDto advertiserDto) {
        return webUserDao.listAdvertiser4Check(advertiserDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkAdvertiser(WebUserCheckLog webUserCheckLog) {
        LoginSysUser sysUser = RequestUserHolder.currentSysUser();
        Preconditions.checkNotNull(sysUser, "当前用户未登录或登录超时，请重新登录！");
        WebUser webUser = new WebUser();
        webUser.setId(webUserCheckLog.getWebUserId());
        webUser.setCheckStatus(Integer.valueOf(webUserCheckLog.getToStatus()));
        webUser.setCheckResult(webUserCheckLog.getCheckResult());
        webUser.setUpdateTime(new Date());
        webUser.setUpdateBy(sysUser.getId());
        webUser.setUpdateName(sysUser.getUserName());
        Integer integer = webUserDao.updateWebUser(webUser);
        if (integer > 0) {
            webUserCheckLog.setCreateTime(new Date());
            webUserCheckLog.setCreateBy(sysUser.getId());
            webUserCheckLog.setCreateName(sysUser.getUserName());
            webUserCheckLogMapper.insertSelective(webUserCheckLog);
        }
    }
}
