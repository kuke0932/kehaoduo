import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

/**
 * @author zhaokaiyuan
 * @create 2017-12-11 14:05
 **/

public class CreateMenu {

    private static Logger logger = LoggerFactory.getLogger(CreateMenu.class);
    public void test1() {
        System.out.println(new BigDecimal("0.3652").scale());
        System.out.println(new BigDecimal("10.352").scale());
        System.out.println(new BigDecimal("0.3652").scale());
        System.out.println(new BigDecimal("0.").scale());
    }

    @Test
    public void test() throws UnsupportedEncodingException {

        String dev = "abcky.site";
        String prod = "kehaoduo.4008603637.com";
        String devAppid = "wxf06ed430ea6952c9";
        String prodAppid = "wx1b0723ab8a95d54a";

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";
        String redirect = "http://%s/web/chose_login.html";
//        String redirect = "http://%s/bs/admin/bindWechatOpenid";
        String s1 = String.format(url, devAppid, URLEncoder.encode(String.format(redirect, dev), "utf-8"),devAppid);
        String s2 = String.format(url, prodAppid, URLEncoder.encode(String.format(redirect, prod), "utf-8"),prodAppid);

        logger.debug("dev--- "+s1);
        logger.debug("prod--- "+s2);

    }
}
