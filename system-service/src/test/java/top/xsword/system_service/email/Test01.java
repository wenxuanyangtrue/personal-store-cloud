package top.xsword.system_service.email;

import org.apache.commons.mail.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;

@SpringBootTest
public class Test01 {

    @Test
    public void t1() throws EmailException, MalformedURLException {
        // Create the email message
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("1825590898@qq.com", "thqtylalssgmedad"));
        email.setSSLOnConnect(true);

        email.setFrom("1825590898@qq.com", "1825590898");
        email.addTo("yang1825590898@163.com", "yang1825590898");
        email.setSubject("TestMail");
        email.setCharset("UTF-8");
        // set the html message
        email.setHtmlMsg("<html>欢迎注册个人云存储用户，<a href=\"https://www.baidu.com\">请点击此链接激活您的账号</a></html>");
        // set the alternative message
        email.setTextMsg("欢迎注册个人云存储用户，您的邮件客户端不支持HTML消息，请复制链接去激活您的账号：https://www.baidu.com");
        // send the email
        email.send();
    }

    @Test
    public void t() throws EmailException {
        Email email = new SimpleEmail();

        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("1825590898@qq.com", "thqtylalssgmedad"));
        email.setSSLOnConnect(true);
        email.setFrom("1825590898@qq.com");
        email.setSubject("TestMail");
        email.setMsg("<a href=\"https://www.baidu.com\">请点击此链接激活您的账号</a>");
        email.addTo("yang1825590898@163.com");
        email.send();
    }

}
