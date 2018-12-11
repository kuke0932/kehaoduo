package com.htcs.kehaoduo.bs.service;


import com.htcs.kehaoduo.bs.po.TShop;

/**
 * 店铺业务层接口
 *
 * @author Dragon
 * @create 2017-10-20 13:37
 **/
public interface TShopService {

    /**
     * 添加店铺信息
     *
     * @param shop TShop店铺实体
     * @return 是否添加成功
     */
    int addShop(TShop shop);

    /**
     * 根据id修改店铺信息
     *
     * @param shop TShop实体
     * @return 是否修改成功
     */
    int updateShop(TShop shop);

    /**
     * 根据当前登陆用户id获取店铺信息
     *
     * @return TShop实体
     */
    TShop queryShopByUserId();
}
