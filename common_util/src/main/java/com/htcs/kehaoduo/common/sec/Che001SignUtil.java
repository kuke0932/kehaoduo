package com.htcs.kehaoduo.common.sec;

import com.alibaba.fastjson.JSON;

import java.util.TreeMap;

public class Che001SignUtil {
    /**
     * 获取带签名的加密数据
     *
     * @param source
     * @param privateKey
     * @param merchantAesKey
     * @return
     */
    public static String getSignDate(String source, String privateKey, String merchantAesKey) {
        TreeMap<String, Object> gameMap = new TreeMap<String, Object>();
        gameMap.put("text", source);
        //用自己的私钥s进行签名
        String sign = EncryUtil.handleRSA(gameMap, privateKey);
        gameMap.put("sign", sign);
        String info = JSON.toJSONString(gameMap);
        System.out.println("	签名：" + sign);
        String gamedata = AES.encryptToBase64(info, merchantAesKey);
        System.out.println("	密文：" + gamedata);
        return gamedata;
    }

    /**
     * 用游戏的公钥进行加密
     *
     * @param merchantAesKey
     * @param gamePublickey
     * @return
     * @throws Exception
     */
    public static String getEncryptkey(String merchantAesKey, String gamePublickey) throws Exception {
        String encryptkey = RSA.encrypt(merchantAesKey, gamePublickey);
        return encryptkey;
    }
}	
