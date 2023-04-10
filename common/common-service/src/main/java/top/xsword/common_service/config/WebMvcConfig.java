package top.xsword.common_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.xsword.common_service.interceptor.DownloadInterceptor;
import top.xsword.common_service.properties.UploadProperties;

import javax.annotation.Resource;

/**
 * Author: ywx
 * Create Time: 2023/2/21
 * Description:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    UploadProperties uploadProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/download/**").addResourceLocations("file:" + uploadProperties.getLocation());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DownloadInterceptor()).addPathPatterns("/download/**");
    }
}
