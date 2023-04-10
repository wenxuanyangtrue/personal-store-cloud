package top.xsword.common_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.xsword.common_util.io.IOUtil;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

/**
 * Author: ywx
 * Create Time: 2023/2/25
 * Description:
 */
//@Configuration(proxyBeanMethods = false)
public class SecretKeyConfig {

    @Bean
    public SecretKey secretKey() throws IOException {

        InputStream is = SecretKeyConfig.class.getClassLoader().getResourceAsStream("secret/key");
        String key = IOUtil.getString(is);

        return new SecretKeySpec(Base64.getDecoder().decode(key), "HmacSHA256");
    }
}
