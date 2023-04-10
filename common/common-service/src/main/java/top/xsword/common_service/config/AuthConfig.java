package top.xsword.common_service.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.xsword.common_service.filter.AuthFilter;

/**
 * Data:2022/11/22
 * Author:ywx
 * Description:注册Auth过滤器
 */
@Configuration(proxyBeanMethods = false)
public class AuthConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter(){
        return new FilterRegistrationBean<>(new AuthFilter());
    }
}
