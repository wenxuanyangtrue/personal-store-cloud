package top.xsword.common_service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Data:2022/12/1
 * Author:ywx
 * Description:
 */
@Data
@ConfigurationProperties(prefix = "system.sts")
@Component
public class STSProperties {
    private String regionId;
    private String roleArn; //临时角色的行为权限
}
