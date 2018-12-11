package com.htcs.kehaoduo.common.util;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 发送邮件的测试程序
 *
 * @author lwq
 */
public class EmailUtil {

    public static void sendMessage(String email, Object Subject) throws MessagingException, UnsupportedEncodingException, GeneralSecurityException {
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        /*
         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.126.com");
        // 发件人的账号
        props.put("mail.user", "z629825@126.com");
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", "zhang284680965");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人
        InternetAddress to = new InternetAddress(email);
        message.setRecipient(RecipientType.TO, to);


        String subject = MimeUtility.encodeWord("脑库直播，找回密码。", "UTF-8", "B");
        // 设置邮件标题
        message.setSubject(subject);
        ;

        // 设置邮件的连接
        message.setContent("<a href='http://drnaoku.com/paper/web/initResetPwd.html?email=" + email + "'>点击此处重置密码</a>", "text/html;charset=UTF-8");

        // 发送邮件
        Transport.send(message);
    }
}