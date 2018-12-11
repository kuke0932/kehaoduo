package com.htcs.kehaoduo.bs.service.impl;

import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.bs.mapper.TShopMapper;
import com.htcs.kehaoduo.bs.po.TShop;
import com.htcs.kehaoduo.bs.service.TShopService;
import com.htcs.kehaoduo.common.conf.RequestUserHolder;
import com.htcs.kehaoduo.common.model.LoginWebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 店铺业务层实现
 *
 * @author Dragon
 * @create 2017-10-20 13:38
 **/
@Service
public class TShopServiceImpl implements TShopService {

    @Autowired
    private TShopMapper tShopMapper;

    @Override
    public int addShop(TShop shop) {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        Preconditions.checkNotNull(loginWebUser, "当前用户未登录或登录超时，请重新登录！");
        Integer userId = loginWebUser.getId();
        shop.setCreateBy(userId);
        shop.setCreateName(loginWebUser.getUserName());
        shop.setWebUserId(userId);
        shop.setCreateTime(new Date());
        return tShopMapper.insertSelective(shop);
    }

    @Override
    public int updateShop(TShop shop) {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        Preconditions.checkNotNull(loginWebUser, "当前用户未登录或登录超时，请重新登录！");
        shop.setUpdateBy(loginWebUser.getId());
        shop.setUpdateName(loginWebUser.getUserName());
        shop.setUpdateTime(new Date());
        return tShopMapper.updateByPrimaryKeySelective(shop);
    }

    @Override
    public TShop queryShopByUserId() {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        Preconditions.checkNotNull(loginWebUser, "当前用户未登录或登录超时，请重新登录！");
        return tShopMapper.selectByWebUserId(loginWebUser.getId());
    }
}
