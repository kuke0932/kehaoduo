package com.htcs.kehaoduo.common;


import com.htcs.kehaoduo.common.util.PropertiesUtil;

public class CacheConstans {
    /**
     * 前台登录用户memCache前缀
     */
    public static final String MEMFIX = PropertiesUtil.getProperty("memfix");
    /**
     * 存cookie web端用户
     */
    public static final String WEB_USER_LOGIN_PREFIX = MEMFIX + "web_user_login_";
    public static final int USER_TIME = Integer.parseInt(PropertiesUtil.getProperty("USER_TIME"));//前台登录用户缓存6小时
    /**
     * 存储cookie 系统用户
     */
    public static final String SYS_USER_LOGIN_PREFIX = MEMFIX + "sys_user_login_";

    /**
     * x-auth-token header
     */
    public static final String X_AUTH_TOKEN = "x-auth-token";

    /**
     * 后台所有用户权限缓存名前缀
     **/
    public static final String SYS_ALL_USER_FUNCTION_PREFIX = MEMFIX + "SYS_USER_ALL_FUNCTION_";
    /**
     * 登录用户权限缓存名前缀
     **/
    public static final String USER_FUNCTION_PREFIX = MEMFIX + "USER_ALL_FUNCTION";
    /**
     * 前台首页 网校名师 缓存
     **/
    public static final String INDEX_TEACHER_RECOMMEND = MEMFIX + "INDEX_TEACHER_RECOMMEND";
    /**
     * 文章  好文推荐 缓存
     **/
    public static final String ARTICLE_GOOD_RECOMMEND = MEMFIX + "ARTICLE_GOOD_RECOMMEND";
    /**
     * 问答  热门问答推荐 缓存
     **/
    public static final String QUESTIONS_HOT_RECOMMEND = MEMFIX + "QUESTIONS_HOT_RECOMMEND";

    /**
     * 网站统计
     */
    public static final String WEB_STATISTICS = MEMFIX + "web_statistics";
    /**
     * 网站最近30条活跃统计
     */
    public static final String WEB_STATISTICS_THIRTY = MEMFIX + "web_statistics_thirty";
    /**
     * 缓存1小时
     */
    public static final int WEB_STATISTICS_TIME = Integer.parseInt(PropertiesUtil.getProperty("WEB_STATISTICS_TIME"));
    /**
     * 默认用的缓存
     */
    public static final String DEFAULT_CACHE = "__DEFAULT__";
    /**
     * 后台登录用户缓存
     */
    public static final String SYSTEM_CACHE = "sysUserCache";
    /**
     * 前台登录用户缓存
     */
    public static final String WEB_CACHE = "webUserCache";

    /**
     * LSS服务列表
     */
    public static final String SYS_LSS_LIST = MEMFIX + "SYS_LSS_LIST";

    /**
     * 文件令牌
     */
    public static final String FILETOKEN = "fileToken";
}
