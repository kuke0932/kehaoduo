package com.htcs.kehaoduo.bs.controller;

import com.alibaba.fastjson.JSONObject;
import com.htcs.kehaoduo.bs.po.TShop;
import com.htcs.kehaoduo.bs.service.TShopService;
import com.htcs.kehaoduo.common.CacheConstans;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.common.conf.RequestUserHolder;
import com.htcs.kehaoduo.common.model.LoginWebUser;
import com.htcs.kehaoduo.common.util.CacheUtil;
import com.htcs.kehaoduo.common.util.token.JWTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 店铺控制层
 *
 * @author Dragon
 * @create 2017-10-20 11:43
 **/
@RestController
@RequestMapping("shop")
public class TShopController {

    @Autowired
    private TShopService tShopService;

    /**
     * 新增店铺信息
     *
     * @param shop TShop实体
     * @return JSON数据状态
     */
    @PostMapping("/addShop")
    public ReturnStatus addShop(TShop shop) {
        tShopService.addShop(shop);

        //保存店铺登录系统
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        String userKey = loginWebUser.getLoginName() + ".web";
        String token = JWTokenUtil.createToken(userKey);

        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).put(CacheConstans.SYSTEM_CACHE, userKey, loginWebUser);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", loginWebUser.getId());
        jsonObject.put("token", token);
        jsonObject.put("lastLoginType", loginWebUser.getLastLoginType());

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "添加成功", jsonObject);


    }

    /**
     * 通过id修改店铺信息
     *
     * @param shop TShop实体
     * @return JSON数据状态
     */
    @PostMapping("/updateShop")
    public ReturnStatus updateShop(TShop shop) {
        tShopService.updateShop(shop);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "修改成功");
    }

    /**
     * 根据userId获取店铺信息
     *
     * @return JSON数据状态
     */
    @GetMapping("/queryShopByUserId")
    public ReturnStatus<TShop> queryShopByUserId() {
        TShop shop = tShopService.queryShopByUserId();
        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "查询成功", shop);
    }


}
