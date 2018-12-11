package com.htcs.kehaoduo.system.service.impl;

import com.htcs.kehaoduo.common.util.MD5Util;
import com.htcs.kehaoduo.common.util.RandomStrUtils;
import com.htcs.kehaoduo.system.bean.SysUser;
import com.htcs.kehaoduo.system.bean.SysUserModel;
import com.htcs.kehaoduo.system.mapper.SysUserMapper;
import com.htcs.kehaoduo.system.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zhaokaiyuan
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> findSysUserList(SysUserModel user) {
        return null;
    }

    /**
     * 根据用户ID，获取用户信息
     */
    @Override
    public SysUser getUserById(Integer userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    /**
     * 根据用户ID，获取用户信息
     */
    @Override
    public SysUser getUserByLoginName(String LoginName) {
        return sysUserMapper.selectByLoginName(LoginName);
    }

    /**
     * 添加用户
     */
    @Override
    public boolean addUser(SysUser userModel) {

        userModel.setSalt(RandomStrUtils.generateStr(6));
        userModel.setLoginPwd(MD5Util.MD5Encode(userModel.getLoginPwd()));
        int ret = sysUserMapper.insertSelective(userModel);
        return ret > 0 ? true : false;
    }

    /**
     * 更新用户
     */
    @Override
    public boolean updateUser(SysUser userModel) {
        int ret = sysUserMapper.updateByPrimaryKeySelective(userModel);
        return ret > 0 ? true : false;
    }

    /**
     * 删除用户
     */
    @Override
    public boolean deleteUserById(Integer userId) {
        int ret = sysUserMapper.deleteByPrimaryKey(userId);
        return ret > 0 ? true : false;
    }

    @Override
    public boolean updatePassword(String username, String oldPwd, String newPwd) {
        SysUser sysUser = sysUserMapper.selectByLoginName(username);
        boolean equals = Objects.equals(DigestUtils.md5Hex(sysUser.getSalt() + oldPwd), sysUser.getLoginPwd());
        if (equals) {
            sysUser.setLoginPwd(DigestUtils.md5Hex(sysUser.getSalt() + newPwd));
            int ret = sysUserMapper.updateByPrimaryKeySelective(sysUser);
            return ret > 0 ? true : false;
        }
        return false;
    }


}
