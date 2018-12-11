package com.htcs.kehaoduo.common.util;

import com.google.common.collect.Maps;
import com.htcs.kehaoduo.common.Common;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class WebUtils {

    public static String MYDOMAIN = null;

    public static void setCookie(HttpServletResponse response, String key, String value, int days) {
        setCookie(response, key, value, days, MYDOMAIN);
    }

    /**
     * 获取header
     *
     * @param request
     * @param key
     * @return
     */
    public static String getHeader(HttpServletRequest request, String key) {
        String header = request.getHeader(key);
        return header;
    }

    /**
     * 设置header
     *
     * @param response
     * @param key
     * @param value
     */
    public static void setHeader(HttpServletResponse response, String key, String value) {
        response.setHeader(key, value);
    }


    /**
     * 设置Cookie值
     */
    public static void setCookie(HttpServletResponse response, String key, String value, int days, String domain) {
        if ((key != null) && (value != null)) {

            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge(days * 24 * 60 * 60);
            cookie.setPath("/");
            if (!StringUtils.isEmpty(domain)) {
                cookie.setDomain(domain);
            }
            response.addCookie(cookie);
        }
    }

    /**
     * 获取Cookie值
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        String resValue = "";
        if ((cookies != null) && (cookies.length > 0)) {
            for (int i = 0; i < cookies.length; i++) {
                if ((!key.equalsIgnoreCase(cookies[i].getName()))
                        || (StringUtils.isEmpty(cookies[i].getValue()))) {
                    continue;
                }
                resValue = cookies[i].getValue();
            }

        }
        return resValue;
    }

    /**
     * 删除默认域名下的
     */
    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response, String name) {
        deleteCookieDomain(request, response, name, MYDOMAIN);
    }

    /**
     * 删除某域下的Cookie
     */
    public static void deleteCookieDomain(HttpServletRequest request,
                                          HttpServletResponse response, String name, String domain) {
        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && (cookies.length > 0)) {
            for (int i = 0; i < cookies.length; i++) {
                if (!name.equalsIgnoreCase(cookies[i].getName())) {
                    continue;
                }
                Cookie ck = new Cookie(cookies[i].getName(), null);
                ck.setPath("/");
                if (!StringUtils.isEmpty(domain)) {
                    ck.setDomain(domain);
                }
                ck.setMaxAge(0);
                response.addCookie(ck);
                return;
            }
        }
    }


    /**
     * 获取请求IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if ((ipAddress == null) || (ipAddress.length() == 0)
                || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if ((ipAddress == null) || (ipAddress.length() == 0)
                || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ipAddress == null) || (ipAddress.length() == 0)
                || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress)) {
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }
        if ((ipAddress != null) && (ipAddress.length() > 15)) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


    /**
     * 获取请求URL
     */
    public static String getServletRequestUrlParms(HttpServletRequest request) {
        StringBuffer sbUrlParms = request.getRequestURL();
        sbUrlParms.append("?");
        @SuppressWarnings("unchecked")
        Enumeration<String> parNames = request.getParameterNames();
        while (parNames.hasMoreElements()) {
            String parName = parNames.nextElement().toString();
            try {
                sbUrlParms.append(parName).append("=")
                        .append(URLEncoder.encode(request.getParameter(parName), "UTF-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
        return sbUrlParms.toString();
    }

    /**
     * 获取请求URI
     */
    public static String getServletRequestUriParms(HttpServletRequest request) {
        StringBuffer sbUrlParms = new StringBuffer(request.getRequestURI());
        sbUrlParms.append("?");
        @SuppressWarnings("unchecked")
        Enumeration<String> parNames = request.getParameterNames();
        while (parNames.hasMoreElements()) {
            String parName = parNames.nextElement().toString();
            try {
                sbUrlParms.append(parName).append("=")
                        .append(URLEncoder.encode(request.getParameter(parName), "UTF-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
        return sbUrlParms.toString();
    }

    /**
     * 根据请求获取所有请求参数
     *
     * @param httpServletRequest 请求
     * @return 请求参数map
     */
    public static HashMap<String, String> getParamMapFromRequest(HttpServletRequest httpServletRequest) {
        if (ObjectUtils.isEmpty(httpServletRequest)) {
            return Maps.newHashMap();
        }

        Map requestParams = httpServletRequest.getParameterMap();
        HashMap<String, String> params = new HashMap<>();

        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";

            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        return params;
    }

    /**
     * 后台用户登录号验证
     */
    public static boolean checkLoginName(String value) {
        return value.matches(Common.loginRegex);
    }

    /**
     * 正则表达试验证邮箱号
     */
    public static boolean checkMobile(String value) {
        return (value.matches(Common.telRegex));
    }

    /**
     * 正则表达试验证邮箱号
     */
    public static boolean checkEmail(String value, int length) {
        if (length == 0) {
            length = 40;
        }
        return (value.matches(Common.emailRegex)) && (value.length() <= length);
    }

    /**
     * 正则表达试验证密码
     */
    public static boolean isPasswordAvailable(String password) {
        String partten = "^[_0-9a-zA-Z]{6,}$";
        boolean flag = (password.matches(partten)) && (password.length() >= 6) && (password.length() <= 16);
        return flag;
    }

    /**
     * 判断如果是ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String her = request.getHeader("x-requested-with");

        return !StringUtils.isEmpty(her);
    }

    /**
     * 判断如果不是ajax请求
     */
    public static boolean isNotAjaxRequest(HttpServletRequest request) {
        return !isAjaxRequest(request);
    }

    /**
     * 获取用户访问浏览器信息
     */
    public static String getUserAgent(HttpServletRequest request) {
        String uabrow = request.getHeader("User-Agent");//获取浏览器信息

        UserAgent userAgent = UserAgent.parseUserAgentString(uabrow);
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();
        return browser.getName().toLowerCase() + ";" + os.getName().toLowerCase();
    }

    /**
     * 去html
     */
    public static String replaceTagHTML(String src) {
        String regex = "\\<(.+?)\\>";
        return StringUtils.isEmpty(src) ? "" : src.replaceAll(regex, "");
    }

    /**
     * 获取cook的值，用于websock接口
     *
     * @param cookieArray
     * @param webUserLoginPrefix
     * @return
     */
    public static String getCookie(String[] cookieArray,
                                   String webUserLoginPrefix) {
        for (String cookie : cookieArray) {
            String[] cook = cookie.split("=");
            if (webUserLoginPrefix.equals(cook[0].trim())) {
                return cook[1];
            }
        }
        return null;
    }
}