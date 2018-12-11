package com.htcs.kehaoduo.system.dao.impl;

import com.htcs.kehaoduo.system.bean.WebUser;
import com.htcs.kehaoduo.system.dao.WebUserDAO;
import com.htcs.kehaoduo.system.dto.AdvertiserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zhaokaiyuan
 * @create 2017-10-20 10:21
 **/
@Repository
public class WebUserDaoImpl implements WebUserDAO {

    @Autowired
    SqlSession sqlSession;

    @Override
    public WebUser getWebUserById(Integer id) {
        return sqlSession.selectOne("WebUserMapper.getWebUserById", id);
    }

    @Override
    public WebUser getWebUserByLoginName(String loginName) {
        return sqlSession.selectOne("WebUserMapper.getWebUserByLoginName", loginName);
    }

    @Override
    public WebUser getWebUserByMobile(String mobile) {
        return sqlSession.selectOne("WebUserMapper.getWebUserByMobile", mobile);
    }

    @Override
    public List<WebUser> findWebUserList(WebUser webUser) {
        return sqlSession.selectList("WebUserMapper.findWebUserList", webUser);
    }

    @Override
    public Integer addWebUser(WebUser webUser) {
        return sqlSession.insert("WebUserMapper.addWebUser", webUser);
    }

    @Override
    public Integer addWebUserExt(WebUser webUser) {
        return sqlSession.insert("WebUserMapper.addWebUserExt", webUser);
    }

    @Override
    public Integer updateWebUser(WebUser webUser) {
        return sqlSession.update("WebUserMapper.updateWebUser", webUser);

    }

    @Override
    public Integer updateWebUserExt(WebUser webUser) {
        return sqlSession.update("WebUserMapper.updateWebUserExt", webUser);
    }

    @Override
    public Integer logicalDeleteWebUserById(Integer id) {
        return sqlSession.delete("WebUserMapper.logicalDeleteWebUserById", id);
    }

    @Override
    public Integer deleteWebUserById(Integer id) {
        return sqlSession.delete("WebUserMapper.deleteWebUserById", id);
    }

    @Override
    public WebUser getWebUserByOpenidA(String openId) {
        return sqlSession.selectOne("WebUserMapper.getWebUserByOpenidA", openId);
    }

    @Override
    public WebUser getWebUserByOpenidB(String openId) {
        return sqlSession.selectOne("WebUserMapper.getWebUserByOpenidB", openId);
    }

    @Override
    public List<Map<String, Object>> listAdvertiser(AdvertiserDto advertiserDto) {
        return sqlSession.selectList("WebUserMapper.listAdvertiser", advertiserDto);
    }

    @Override
    public List<Map<String, Object>> listAdvertiser4Check(AdvertiserDto advertiserDto) {
        return sqlSession.selectList("WebUserMapper.listAdvertiser4Check", advertiserDto);
    }

}
