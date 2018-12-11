package com.htcs.kehaoduo.system.bean;


import com.htcs.kehaoduo.common.model.LoginSysFunction;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaokaiyuan
 */
public class SysFunction extends LoginSysFunction implements Serializable {
    private List childrenList;

    @Override
    public List getChildrenList() {
        return childrenList;
    }

    @Override
    public void setChildrenList(List childrenList) {
        this.childrenList = childrenList;
    }
}
