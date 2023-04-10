package top.xsword.common_service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Data:2022/11/19
 * Author:ywx
 * Description:OSS的配置实体类
 */
@Data
@ConfigurationProperties(prefix = "system.oss")
@Component
public class OSSProperties {
    private String regionId;
    private String accessKey;
    private String accessKeySecret;
    private String bucket;
    private String bucketDomain;
    private String endpoint;
    private Integer uploadExpireTime = 10; //限制参数的生效时间，单位为分钟，默认为10分钟
    private Integer maxSize = 100; //限制上传文件的大小，单位为MB，默认为100M
    private String callbackUrl;
    private Integer downloadExpireTime = 10; //限制参数的生效时间，单位为分钟，默认为10分钟
}
