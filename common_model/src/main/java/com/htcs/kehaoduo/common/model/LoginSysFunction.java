package com.htcs.kehaoduo.common.model;

import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by htcs on 2017/7/25.
 */
public class LoginSysFunction extends BaseModel {
    private static final long serialVersionUID = 1L;
    /**
     * 权限ID
     */
    private Integer id;
    /**
     * 权限父ID
     */
    private Integer parentId;
    /**
     * 权限名
     */
    private String functionName;
    /**
     * 权限路径
     */
    private String functionUrl;
    /**
     * 权限类型 1菜单 2功能
     */
    private Integer functionType;
    /**
     * 排序
     */
    private Integer functionSort;
    private String clickFunction;
    private String buttonStyle;
    private String iconStyle;
    private Integer isVisible;

    /**
     * 子菜单
     */
    private List<LoginSysFunction> childrenList;

    public List<LoginSysFunction> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<LoginSysFunction> childrenList) {
        this.childrenList = childrenList;
    }

    public Integer getId() {
        return id;
    }

    public void setFunctionId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    public Integer getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Integer functionType) {
        this.functionType = functionType;
    }

    public Integer getFunctionSort() {
        return functionSort;
    }

    public void setFunctionSort(Integer functionSort) {
        this.functionSort = functionSort;
    }

    public String getClickFunction() {
        return clickFunction;
    }

    public void setClickFunction(String clickFunction) {
        this.clickFunction = clickFunction;
    }

    public String getButtonStyle() {
        return buttonStyle;
    }

    public void setButtonStyle(String buttonStyle) {
        this.buttonStyle = buttonStyle;
    }

    public String getIconStyle() {
        return iconStyle;
    }

    public void setIconStyle(String iconStyle) {
        this.iconStyle = iconStyle;
    }

    public Integer getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }


    public LoginSysFunction copyOf() {
        LoginSysFunction loginSysFunction = new LoginSysFunction();
        BeanUtils.copyProperties(this, loginSysFunction);
        return loginSysFunction;
    }
}
