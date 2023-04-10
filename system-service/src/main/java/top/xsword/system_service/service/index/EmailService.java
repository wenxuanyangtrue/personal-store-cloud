package top.xsword.system_service.service.index;

import org.apache.commons.mail.EmailException;

/**
 * Author: ywx
 * Create Time: 2022/12/13
 * Description:发送用户激活的邮件
 */
public interface EmailService {

    /**
     * 发送html邮件
     * @param userEmail
     * @param link
     */
    void sendHTML(String userEmail, String link) throws EmailException;
}
