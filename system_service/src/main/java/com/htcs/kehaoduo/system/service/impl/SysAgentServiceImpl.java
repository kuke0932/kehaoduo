package com.htcs.kehaoduo.system.service.impl;

import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.common.conf.RequestUserHolder;
import com.htcs.kehaoduo.common.model.LoginSysUser;
import com.htcs.kehaoduo.common.util.RandomStrUtils;
import com.htcs.kehaoduo.system.bean.SysAgent;
import com.htcs.kehaoduo.system.bean.SysAgentAppid;
import com.htcs.kehaoduo.system.bean.SysUser;
import com.htcs.kehaoduo.system.mapper.SysAgentAppidMapper;
import com.htcs.kehaoduo.system.mapper.SysAgentMapper;
import com.htcs.kehaoduo.system.mapper.SysUserMapper;
import com.htcs.kehaoduo.system.service.SysAgentService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhaokaiyuan
 * @create 2017-12-07 11:30
 **/
@Service
public class SysAgentServiceImpl implements SysAgentService {

    @Autowired
    SysAgentMapper sysAgentMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
	SysAgentAppidMapper sysAgentAppidMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAgent(SysAgent sysAgent, SysAgentAppid sysAgentAppid) {

		Preconditions.checkArgument(StringUtils.isNotEmpty(sysAgent.getLoginName()), "账号不能为空，请重新输入！");
		Preconditions.checkArgument(StringUtils.isNotEmpty(sysAgent.getLoginPwd()), "密码不能为空，请重新输入！");
		Preconditions.checkArgument(StringUtils.isNotEmpty(sysAgent.getArea()), "代理区域不能为空，请重新输入！");
        int count = sysUserMapper.countByLoginName(sysAgent.getLoginName());
        if (count > 0) {
            return false;
        }
        SysUser sysUser = new SysUser();
        Date now = new Date();
        String salt = RandomStrUtils.generateStr(6);
        sysUser.setLoginName(sysAgent.getLoginName());
        sysUser.setSalt(salt);
        sysUser.setLoginPwd(DigestUtils.md5Hex(salt + sysUser.getLoginPwd()));
        sysUser.setMobile(sysAgent.getMobile());
        sysUser.setTelephone(sysAgent.getTelephone());
        sysUser.setCreateTime(now);
        int ret = sysUserMapper.insertSelective(sysUser);
        if (ret > 0) {
            Integer sysUserId = sysUser.getId();
            sysAgent.setSysUserId(sysUserId);
            sysAgent.setCreateTime(now);
            int i = sysAgentMapper.insertSelective(sysAgent);
			sysAgentAppid.setSysUserId(sysUserId);
			sysAgentAppid.setCreateTime(now);
			sysAgentAppidMapper.insertSelective(sysAgentAppid);
            return i > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean addAgent(String username, String password, String area) {
        SysAgent sysAgent = new SysAgent();
        sysAgent.setLoginName(username);
        sysAgent.setLoginPwd(password);
        sysAgent.setArea(area);
        return addAgent(sysAgent, null);
    }

    @Override
    public boolean updateAgent(SysAgent sysAgent) {
        LoginSysUser sysUser = RequestUserHolder.currentSysUser();
        Preconditions.checkNotNull(sysUser, "登录超时，请重新登录！");
        sysAgent.setUpdateName(sysUser.getLoginName());
        sysAgent.setUpdateTime(new Date());
        sysAgent.setUpdateBy(sysUser.getId());
        int i = sysAgentMapper.updateBySysUserId(sysAgent);
        return i > 0 ? true : false;
    }

    @Override
    public boolean updateAgentWithoutLogged(SysAgent sysAgent) {
        sysAgent.setUpdateTime(new Date());
        int i = sysAgentMapper.updateBySysUserId(sysAgent);
        return i > 0 ? true : false;
    }


    @Override
    public List<Map<String, Object>> listAgent(String nameOrTel,Date beginTime, Date endTime) {
        return sysAgentMapper.listAgent(nameOrTel, beginTime, endTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAgentAppid(SysAgentAppid sysAgentAppid, String mobile, String telephone) {
        LoginSysUser sysUser = RequestUserHolder.currentSysUser();
        Preconditions.checkNotNull(sysUser, "登录超时，请重新登录！");
        Preconditions.checkNotNull(sysAgentAppid.getSysUserId(), "sysUserId参数不能为空，请重新输入！");
        Date now = new Date();
        sysAgentAppid.setUpdateTime(now);
        sysAgentAppid.setUpdateBy(sysUser.getId());
        sysAgentAppid.setUpdateName(sysUser.getUserName());
        sysAgentAppidMapper.updateBySysUserId(sysAgentAppid);

        SysAgent sysAgent = new SysAgent();
        sysAgent.setSysUserId(sysAgentAppid.getSysUserId());
        sysAgent.setArea(sysAgentAppid.getArea());
        sysAgent.setAreaCode(sysAgentAppid.getAreaCode());
        sysAgent.setUpdateTime(now);
        sysAgent.setUpdateBy(sysUser.getId());
        sysAgent.setUpdateName(sysUser.getUserName());
        sysAgentMapper.updateBySysUserId(sysAgent);

        SysUser user = new SysUser();
        user.setId(sysAgentAppid.getSysUserId());
        user.setMobile(mobile);
        user.setTelephone(telephone);
        user.setUpdateBy(sysUser.getId());
        user.setUpdateName(sysUser.getUserName());
        user.setUpdateTime(now);
        sysUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void logicalDelAgent(Integer sysUserId) {
        Preconditions.checkNotNull(sysUserId, "sysUserId参数不能为空，请重新输入！");
        sysUserMapper.logicalDelAgent(sysUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchRemoveAgent(String ids) {
        Preconditions.checkNotNull(ids, "缺少数据！");
        String[] split = ids.split(",");
        for (String aSplit : split) {
            logicalDelAgent(Integer.valueOf(aSplit));
        }
    }

    @Override
    public Map<String, Object> queryAgentByUserId(Integer id) {
        return sysAgentMapper.queryAgentByUserId(id);
    }
}
