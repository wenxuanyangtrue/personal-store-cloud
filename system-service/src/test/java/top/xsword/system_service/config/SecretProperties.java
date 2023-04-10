package top.xsword.system_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import top.xsword.common_service.config.SecretKeyConfig;
import top.xsword.common_util.io.IOUtil;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * Author: ywx
 * Create Time: 2023/2/25
 * Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "system.secret")
public class SecretProperties {
    private String path;

    private SecretKey secretKey;

    @PostConstruct
    public void initSecretKey() throws IOException {
        InputStream is = SecretKeyConfig.class.getClassLoader().getResourceAsStream("secret/key");
        String key = IOUtil.getString(is);
       this.secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "HmacSHA256");
    }
}
