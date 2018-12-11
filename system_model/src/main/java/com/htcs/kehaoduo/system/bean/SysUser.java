package com.htcs.kehaoduo.system.bean;


import com.htcs.kehaoduo.common.model.LoginSysUser;

import java.io.Serializable;

/**
 * 后台系统用户实体
 *
 * @author
 */
public class SysUser extends LoginSysUser implements Serializable {


    @Override
    public LoginSysUser copyOf() {
        return super.copyOf();
    }
}
