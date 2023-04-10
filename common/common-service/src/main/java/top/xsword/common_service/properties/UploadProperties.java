package top.xsword.common_service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: ywx
 * Create Time: 2023/2/15
 * Description:
 */
@Data
@ConfigurationProperties(prefix = "system.upload")
@Component
public class UploadProperties {
    private String location;
}
