package top.xsword.common_service.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.auth.DefaultCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import top.xsword.common_service.properties.OSSProperties;

/**
 * Data:2022/11/20
 * Author:ywx
 * Description:
 */

@Configuration(proxyBeanMethods = false)
public class OSSConfig {

    @Bean
    public OSSClient ossClient(OSSProperties ossProperties) {
        //创建默认凭证对象，传入accessKey和accessKeySercret
        DefaultCredentials credentials = new DefaultCredentials(
                ossProperties.getAccessKey(),
                ossProperties.getAccessKeySecret());
        //创建默认凭证行为，传入凭证
        DefaultCredentialProvider credentialProvider = new DefaultCredentialProvider(credentials);
        //返回创建的OSSClient对象，传入endpoint和凭证行为
        return new OSSClient(ossProperties.getEndpoint(), credentialProvider,null);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory connectionFactory){
        //重新设置Redis的数据模板，让其可以操作Java中的对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
