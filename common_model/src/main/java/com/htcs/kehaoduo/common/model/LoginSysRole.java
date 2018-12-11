package com.htcs.kehaoduo.common.model;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by htcs on 2017/7/25.
 */
public class LoginSysRole extends BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer id;
    /**
     * 角色名字
     */
    private String roleName;
    /**
     * 父ID
     */
    private Integer parentId;
    /**
     * 角色值
     */
    private String roleValue;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 角色描述
     */
    private Integer roleType;
    /**
     * 逻辑删除标识
     */

    private List<LoginSysRole> childrenList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public List<LoginSysRole> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<LoginSysRole> childrenList) {
        this.childrenList = childrenList;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (obj instanceof LoginSysRole) {
            if (Objects.equals(((LoginSysRole) obj).getId(), this.getId())
                    || (((LoginSysRole) obj).getId() == null && Objects.equals(((LoginSysRole) obj).getRoleValue(), this.getRoleValue()))) {
                return true;
            }
            return false;
        }
        return false;
    }


    public LoginSysRole copyOf() {
        LoginSysRole loginSysRole = new LoginSysRole();
        BeanUtils.copyProperties(this, loginSysRole);
        return loginSysRole;
    }
}
