package com.htcs.kehaoduo.system.bean;

import com.htcs.kehaoduo.common.model.LoginWebUser;

import java.io.Serializable;

/**
 * @author zhaokaiyuan
 * @create 2017-10-20 10:10
 **/
public class WebUser extends LoginWebUser implements Serializable {
    /*@Override
    public WebUser copyOf() {
        WebUser user=new WebUser();
        BeanUtils.copyProperties(super.instance() , user);
        return user;
    }*/

    @Override
    public LoginWebUser copyOf() {
        return super.copyOf();
    }
}
