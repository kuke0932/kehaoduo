package com.htcs.kehaoduo.system.bean;

import com.htcs.kehaoduo.common.model.LoginSysRole;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色
 *
 * @author taotao
 */
public class SysRole extends LoginSysRole implements Serializable {
    private List childrenList;

    @Override
    public List getChildrenList() {
        return childrenList;
    }

    @Override
    public void setChildrenList(List childrenList) {
        this.childrenList = childrenList;
    }

    @Override
    public LoginSysRole copyOf() {
        return super.copyOf();
    }
}
