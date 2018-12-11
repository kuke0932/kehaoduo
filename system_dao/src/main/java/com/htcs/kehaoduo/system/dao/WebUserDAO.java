package com.htcs.kehaoduo.system.dao;

import com.htcs.kehaoduo.system.bean.WebUser;
import com.htcs.kehaoduo.system.dto.AdvertiserDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhaokaiyuan
 * @create 2017-10-20 10:15
 **/
public interface WebUserDAO {

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
     * 添加用户扩展属性
     *
     * @param webUser
     * @return
     */
    Integer addWebUserExt(WebUser webUser);

    /**
     * 更新用户
     *
     * @param webUser
     * @return
     */
    Integer updateWebUser(WebUser webUser);

    /**
     * 更新用户扩展属性
     *
     * @param webUser
     * @return
     */
    Integer updateWebUserExt(WebUser webUser);

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
     * 根据openId获取用户
     *
     * @param openId
     */
    WebUser getWebUserByOpenidA(String openId);

    WebUser getWebUserByOpenidB(String openId);

    /**
     * 广告主资料列表
     *
     * @param advertiserDto 业务dto
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
}
