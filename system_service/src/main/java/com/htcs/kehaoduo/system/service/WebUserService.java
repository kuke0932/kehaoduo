package com.htcs.kehaoduo.system.service;

import com.htcs.kehaoduo.system.bean.WebUser;
import com.htcs.kehaoduo.system.bean.WebUserCheckLog;
import com.htcs.kehaoduo.system.dto.AdvertiserDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhaokaiyuan
 * @create 2017-10-20 13:51
 **/
public interface WebUserService {

    /**
     * 根据用户id获取用户
     *
     * @param id
     * @return
     */
    WebUser getWebUserById(Integer id);

    /**
     * 根据用户id获取用户
     *
     * @param loginName
     * @return
     */
    WebUser getWebUserByLoginName(String loginName);

    /**
     * 根据用户id获取用户
     *
     * @param mobile
     * @return
     */
    WebUser getWebUserByMobile(String mobile);

    /**
     * 根据openid获取用户
     *
     * @param openId
     * @return
     */
    WebUser getWebUserByOpenidA(String openId);

    /**
     * 查询用户列表
     *
     * @param webUser
     * @return
     */
    List<WebUser> findWebUserList(WebUser webUser);

    /**
     * 添加用户
     *
     * @param webUser
     * @return
     */
    Integer addWebUser(WebUser webUser);

    /**
     * 更新用户
     *
     * @param webUser
     * @return
     */
    Integer updateWebUser(WebUser webUser);

    /**
     * 根据id逻辑删除
     *
     * @param id
     * @return
     */
    Integer logicalDeleteWebUserById(Integer id);

    /**
     * 根据id物理删除
     *
     * @param id
     * @return
     */
    Integer deleteWebUserById(Integer id);

    /**
     * 是否已经注册
     *
     * @param mobile
     * @return
     */
    boolean isRegistered(String mobile);


    /**
     * 根据ID修改手机号
     *
     * @param id
     * @param mobile
     * @return
     */
    Integer updateMobileById(Integer id, String mobile);

    /**
     * 根据ID修改手机号
     *
     * @param pwd 原始密码
     * @param id  手机号
     * @return
     */
    Integer updatePasswordById(Integer id, String pwd);


    WebUser getWebUserByOpenidB(String openId);

    /**
     * 广告主资料列表
     *
     * @param advertiserDto
     * @return
     */
    List<Map<String, Object>> listAdvertiser(AdvertiserDto advertiserDto);

    /**
     * 查看广告主的资料 审核
     *
     * @param advertiserDto
     * @return
     */
	List<Map<String,Object>> listAdvertiser4Check(AdvertiserDto advertiserDto);

    /**
     * 审核广告主
     *
     * @param webUserCheckLog
     */
	void checkAdvertiser(WebUserCheckLog webUserCheckLog);
}



