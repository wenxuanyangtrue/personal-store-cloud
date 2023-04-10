package top.xsword.common_service.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import top.xsword.common_service.filter.ExceptionHandlerFilter;

/**
 * Data:2022/11/24
 * Author:ywx
 * Description:注册全局异常处理过滤器，第一个过滤器
 */
@Configuration(proxyBeanMethods = false)
public class ExceptionHandlerConfig {

    @Bean
    public FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilter() {
        FilterRegistrationBean<ExceptionHandlerFilter> bean =
                new FilterRegistrationBean<>(new ExceptionHandlerFilter());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
