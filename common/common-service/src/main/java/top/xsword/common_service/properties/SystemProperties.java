package top.xsword.common_service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: ywx
 * Create Time: 2023/2/28
 * Description:
 */
@Data
@ConfigurationProperties(prefix = "system")
@Component
public class SystemProperties {
    private String ip;
    private String domain;
    private String port;
    private String protocol;

    public String getUrl() {
        return protocol + "://" + (domain != null ? domain : ip) + ":" + port;
    }
}
