package top.xsword.system_service.service.index.impl;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;
import top.xsword.common_service.properties.EmailProperties;
import top.xsword.system_service.service.index.EmailService;

import javax.annotation.Resource;

/**
 * Author: ywx
 * Create Time: 2022/12/13
 * Description:
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    EmailProperties emailProperties;

    public void sendHTML(String userEmail, String link) throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(emailProperties.getHostName());
        email.setSmtpPort(emailProperties.getPort());
        email.setAuthenticator(new DefaultAuthenticator(emailProperties.getUsername(), emailProperties.getPassword()));
        email.setSSLOnConnect(emailProperties.getSslConnect());
        email.setCharset(emailProperties.getEncoding());
        email.setFrom(emailProperties.getUsername(), "个人云存储");
        email.setSubject("个人云存储账户激活");
        email.addTo(userEmail, userEmail.split("@")[0]); //用户的邮箱及名称

        // set the html message
        email.setHtmlMsg("<html>欢迎注册个人云存储用户，<a href=\"" + link + "\" > 请在24小时内点击此链接激活您的账号 </a></html> ");
        // set the alternative message
        email.setTextMsg("欢迎注册个人云存储用户，您的邮件客户端不支持HTML消息，请复制链接去激活您的账号：" + link + "");
        // send the email
        email.send();
    }
}
