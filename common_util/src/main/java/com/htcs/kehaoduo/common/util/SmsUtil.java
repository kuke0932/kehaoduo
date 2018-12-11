package com.htcs.kehaoduo.common.util;

import com.htcs.kehaoduo.common.Common;
import com.htcs.kehaoduo.common.model.BaseEnum;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author zhaokaiyuan
 * @create 2017-10-24 16:54
 **/
public class SmsUtil {

    static final String URL = "http://v.juhe.cn/sms/send";

    public static String send(String mobile, SmsTemplate smsTemplate) throws IOException {
        String onlyNumber = RandomStrUtils.generateOnlyNumber(6);
        HashMap map = new HashMap();
        map.put("mobile", mobile);
        map.put("tpl_id", smsTemplate.getId());
        map.put("tpl_value", URLEncoder.encode(String.format(smsTemplate.getContent(), onlyNumber), "utf-8"));
        map.put("key", "a96047fde699db7e988cf9a62ce15f14");
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).put(mobile + smsTemplate.getVerifyCodeType(), onlyNumber);
        String post = HttpClientUtil.post(URL, map, "utf-8");
        return post;
    }


    public enum SmsTemplate implements BaseEnum {
        REGISTER("REGISTER", "53087", "#code#=%s", Common.VerifyCodeType.REGISTER),
        CHANGE_MOBILE("CHANGE_MOBILE", "53098", "#code#=%s", Common.VerifyCodeType.CHANGE_MOBILE),
        FORGET_PASSWORD("FORGET_PASSWORD", "53090", "#code#=%s", Common.VerifyCodeType.FORGET_PASSWORD),
        CHANGE_PASSWORD("CHANGE_PASSWORD", "53098", "#code#=%s", Common.VerifyCodeType.CHANGE_PASSWORD);

        private String id;
        private String value;
        private String content;
        private Common.VerifyCodeType verifyCodeType;

        SmsTemplate(String value, String id, String content, Common.VerifyCodeType verifyCodeType) {
            this.id = id;
            this.value = value;
            this.content = content;
            this.verifyCodeType = verifyCodeType;
        }

        public String getId() {
            return id;
        }

        @Override
        public String getValue() {
            return value;
        }

        public String getContent() {
            return content;
        }

        public Common.VerifyCodeType getVerifyCodeType() {
            return verifyCodeType;
        }

        @Override
        public String toString() {
            return "SmsTemplate{" +
                    "id='" + id + '\'' +
                    ", value='" + value + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}


