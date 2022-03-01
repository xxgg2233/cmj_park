package com.cmj.park.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 图片上传路径的配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${upload-path}")
    private String uploadPath;
    @Value("${upload-access-path}")
    private String uploadAccessPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadAccessPath + "**")
                .addResourceLocations("file:" + uploadPath);

    }
}
