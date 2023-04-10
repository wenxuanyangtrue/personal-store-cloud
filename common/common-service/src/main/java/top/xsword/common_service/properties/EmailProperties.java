package top.xsword.common_service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: ywx
 * Create Time: 2022/12/13
 * Description:
 */
@Data
@ConfigurationProperties(prefix = "system.email")
@Component
public class EmailProperties {
    private String username; //邮箱
    private String password; //授权码
    private Integer port;
    private String hostName; //邮件服务器域名
    private Boolean sslConnect; //是否开启ssl加密传输
    private String encoding;
}
