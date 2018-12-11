package com.htcs.kehaoduo.common;

import com.htcs.kehaoduo.common.model.BaseEnum;

public interface Common {
    /**
     * 逻辑删除
     */
    public static Byte LOGICALDEL_YES = 1;
    /**
     * 没有逻辑删除
     */
    public static Byte LOGICALDEL_NO = 0;

    /**
     * 邮箱正则表达式
     */
    public String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    /**
     * 电话号码正则表达式
     */
    public static String telRegex = "^1[0-9]{10}$";
    /**
     * 后台用户登录名正则表达式
     */
    public static String loginRegex = "^([0-9]*[a-zA-Z]+[0-9]*){6,20}$";
    /**
     * 图片验证码Session的K
     */
    public static final String RAND_CODE = "COMMON_RAND_CODE";
    /**
     * 属性文件名
     */
    public static final String FILE_NAME_PROP = "config/memtimes.properties";
    /**
     * 微信登录的配置文件
     */
    public static final String WEIXIN_PROP = "weixinconfig.properties";

    enum VerifyCodeType implements BaseEnum {
        REGISTER("REGISTER", "注册"),
        FORGET_PASSWORD("FORGET_PASSWORD", "找回密码"),
        CHANGE_PASSWORD("CHANGE_PASSWORD", "更改密码"),
        CHANGE_MOBILE("CHANGE_MOBILE", "更改手机号");

        private String value;
        private String content;

        VerifyCodeType(String value, String content) {
            this.value = value;
            this.content = content;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }


    public enum PayType {
        WECHATPAY_NATIVE("WECHATPAY_NATIVE"),
        WECHATPAY_JSAPI("WECHATPAY_JSAPI"),
        ALIPAY_PC("ALIPAY_PC"),
        ALIPAY_WAP("ALIPAY_WAP");

        private String value = "";

        PayType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum OrderStatus {
        SUCCESS("SUCCESS"),
        INIT("INIT"),
        CANCEL("CANCEL");

        private String value = "";

        OrderStatus(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }


}
